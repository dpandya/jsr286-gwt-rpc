/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dushyant.portlet.gwt;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dushyant.portlet.servlet.resolver.RequestResponseResolver;
import org.gwtwidgets.server.spring.RPCServiceExporter;
import org.gwtwidgets.server.spring.RPCServiceExporterFactory;
import org.gwtwidgets.server.spring.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.portlet.handler.HandlerInterceptorAdapter;

/**
 * A gwt-rpc dispatcher interceptor that is responsible for dispatching the incoming portlet resource requests to
 * corresponding GWT-RPC services. The interceptor only intercepts resource requests with a portlet request parameter
 * <b>ajaxMode=rpc</b> Follow the following steps to use this interceptor to use GWT-RPC with portlet resource requests.
 * <ul> <li>On server side, inject an instance of this interceptor class to an instance of a handler mapping in your
 * spring application context file as follows,
 * <p/>
 * <code> bean name="defaultAnnotationHandlerMapping" class="org.springframework.web.portlet.mvc.annotation
 * .DefaultAnnotationHandlerMapping" p:interceptors-ref="gwtRpcDispatcherInterceptor" </code></li><li>Form portlet
 * resource phase url and pass the following two portlet request parameters 1. <b>ajaxMode=rpc</b> and 2.
 * <b>servicePath</b> matching the value of <b>RemoteServiceRelativePath</b> annotation of the service.</li></li>
 * <p/>
 * <li>On client side, in GWT code, override the default GWT-RPC service url with the portlet resource request url
 * created in above step, as follows
 * <p/>
 * <code> SomeGwtServiceAsync service = GWT.create(SomeGwtService.class);
 * <p/>
 * // Change the default URL of the service to go through portlet's resource phase
 * <p/>
 * ((ServiceDefTarget)service).setServiceEntryPoint (portletResourcePhaseUrl); </code> </li> </ul>
 *
 * @author Dushyant Pandya
 */
@Component
public class GwtRpcDispatcherInterceptor extends HandlerInterceptorAdapter
{
    private static final Logger LOG = LoggerFactory.getLogger(GwtRpcDispatcherInterceptor.class);

    /**
     * A resource request parameter name that is used to determine if this resource request is for GWT RPC.
     */
    private static final String PARAM_AJAX_MODE = "ajaxMode";

    /**
     * A resource request parameter value that is used to determine if this resource request is for GWT RPC.
     */
    private static final String RPC = "rpc";

    // An instance of GwtRpcServiceResolver that provides a mapping for GWT-RPC service
    private GwtRpcServiceResolver mappingsProvider = null;

    // An instance of RPCServiceExporterFactory that is responsible for creating instances of
    // RPCServiceExporter to actually encode and decode GWT-RPC requests
    private RPCServiceExporterFactory rpcServiceExporterFactory;

    private RequestResponseResolver requestResponseResolver;

    @Override
    public boolean preHandleResource(ResourceRequest request, ResourceResponse response, Object handler)
        throws Exception
    {
        // Set continueHandlingRequest to true so that if this is not a GWT RPC call then the other interceptors in the
        // chain can continue processing and the Spring Portlet MVC can dispatch the request to corresponding
        // controller.
        boolean continueHandlingRequest = true;

        // Check if this request needs to be treated as a GWT RPC service call

        // This interceptor gets invoked for all the resource requests. We need to process only the GWT RPC requests.
        // Check if this is a GWT RPC call.
        if (isGwtRpcCall(request, response, handler))
        {
            // This resource request is for GWT RPC service call. Set continueHandlingRequest so that Spring
            // does not end up invoking the Controller class as well for this request.
            continueHandlingRequest = false;
            handleGwtRpc(request, response, handler);
        }
        return continueHandlingRequest;
    }

    /**
     * The method that determines if this is a GWT RPC resource request. The default implementation of this method
     * checks for a resource request parameter named "ajaxMode". If the "ajaxMode" is present and if it has a value of
     * "rpc" then it considers this to be a GWT RPC service call.
     *
     * @param request
     * @param response
     * @param handler
     *
     * @return boolean True if this request is a GWT RPC call. False otherwise.
     */
    protected boolean isGwtRpcCall(ResourceRequest request, ResourceResponse response, Object handler)
    {
        return RPC.equalsIgnoreCase(request.getParameter(PARAM_AJAX_MODE));
    }

    /**
     * Get the GWT RPC service instance. The default implementation of this method checks for a resource request
     * parameter named "servicePath" and a map provided by {@link GwtRpcServiceResolver} for finding the GWT RPC
     * service.
     *
     * @param resourceRequest
     * @param resourceResponse
     * @param handler
     *
     * @return An instance GWT RPC service
     */
    protected Object getGwtRpcService(ResourceRequest resourceRequest, ResourceResponse resourceResponse,
        Object handler)
    {
        return mappingsProvider.getGwtRpcService(resourceRequest, resourceResponse);
    }

    /**
     * This method dispatches the resourceRequest to the GWT RPC service call.
     *
     * @param resourceRequest
     * @param resourceResponse
     * @param handler
     *
     * @throws Exception
     */
    protected void handleGwtRpc(ResourceRequest resourceRequest, ResourceResponse resourceResponse, Object handler)
        throws Exception
    {
        // Get the gwt-rpc service instance
        Object service = getGwtRpcService(resourceRequest, resourceResponse, handler);

        // If gwt-rpc service is found then process it
        if (service != null)
        {
            HttpServletRequest servletReq = requestResponseResolver.getHttpServletRequest(resourceRequest);
            HttpServletResponse servletRes = requestResponseResolver.getHttpServletResponse(resourceResponse);

            //Create an instance of RPCServiceExporter using the rpcServiceExporterFactory factory.
            RPCServiceExporter exporter = rpcServiceExporterFactory.create();

            // Set default values to the RPCServiceExporter. The RPCServiceExporter is responsible for
            // actually dispatching the request to GWT-RPC service.
            exporter.setResponseCachingDisabled(isDisableResponseCaching());
            exporter.setServletContext(servletReq.getSession().getServletContext());
            exporter.setServletConfig(null);
            exporter.setService(service);
            exporter.setServiceInterfaces(ReflectionUtils.getExposedInterfaces(service.getClass()));
            exporter.setThrowUndeclaredExceptionToServletContainer(isThrowUndeclaredExceptionToServletContainer());
            exporter.setShouldCheckPermutationStrongName(isCheckPermutationStrongName());

            // Simulate afterPropertiesSet event on the exporter
            exporter.afterPropertiesSet();

            LOG.debug("Dispatching the portlet resource request to GWT-RPC service.... = {}", service.getClass());

            // Let the exporter handle the request
            exporter.handleRequest(servletReq, servletRes);
        }
    }


    /**
     * Sets an instance of GwtRpcServiceResolver. The given instance will be used to find GWT-RPC service path mappings
     * to dispatch incoming portlet resource requests to corresponding GWT-RPC service.
     *
     * @param mappingsProvider
     */
    @Autowired
    public void setMappingsProvider(GwtRpcServiceResolver mappingsProvider)
    {
        this.mappingsProvider = mappingsProvider;
    }

    /**
     * Sets an instance of RPCServiceExporterFactory. The given rpcServiceExporterFactory will be used to create
     * instances of RPCServiceExporter
     *
     * @param rpcServiceExporterFactory
     */
    @Autowired
    public void setRpcServiceExporterFactory(RPCServiceExporterFactory rpcServiceExporterFactory)
    {
        this.rpcServiceExporterFactory = rpcServiceExporterFactory;
    }

    @Autowired
    public void setRequestResponseResolver(RequestResponseResolver requestResponseResolver)
    {
        this.requestResponseResolver = requestResponseResolver;
    }

    /**
     * Can be used to explicitly disable caching of RPC responses in the client by modifying the HTTP headers of the
     * response. .
     */
    protected boolean isDisableResponseCaching()
    {
        return false;
    }

    /**
     * The parameter will be propagated to an RPCServiceExporter that will actually dispatch the portlet request to
     * GWT-RPC service. The default value is <code>false</code>. Please override this method to change the default
     * value.
     *
     * @see {@link org.gwtwidgets.server.spring.RPCServiceExporter#setThrowUndeclaredExceptionToServletContainer
     *      (boolean)}
     */
    protected boolean isThrowUndeclaredExceptionToServletContainer()
    {
        return false;
    }

    /**
     * The parameter will be propagated to an RPCServiceExporter that will actually dispatch the portlet request to
     * GWT-RPC service. The default value is <code>false</code>. Please override this method to change the default
     * value.
     *
     * @see {@link org.gwtwidgets.server.spring.RPCServiceExporter#setShouldCheckPermutationStrongName(boolean)}
     */
    protected boolean isCheckPermutationStrongName()
    {
        return false;
    }
}
