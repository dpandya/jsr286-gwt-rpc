package com.dushyant.portlet.gwt;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.apache.commons.lang.ArrayUtils;
import org.gwtwidgets.server.spring.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * A default implementation of the GwtRpcServiceResolver that scans the spring application context and looks for beans
 * with {@link RemoteServiceRelativePath} annotation.
 * <p/>
 * The implementation then returns a GWT RPC service bean based on the value of the portlet request parameter named
 * <b>servicePath</b>. It returns the bean having the <b>RemoteServiceRelativePath</b> annotation value equal to the
 * <b>servicePath</b> portlet request parameter value.
 * <p/>
 *
 * @author Dushyant Pandya
 */
@Component
public class PathBasedGwtRpcServiceResolver implements GwtRpcServiceResolver, InitializingBean, ApplicationContextAware
{
    private static final Logger LOG = LoggerFactory.getLogger(PathBasedGwtRpcServiceResolver.class);

    /**
     * The spring application context object tobe scanned for GWT RPC service beans
     */
    private ApplicationContext applicationContext;

    /**
     * A flag indicating if the parent application context also needs to be scanned for GWT RPC service beans
     */
    private boolean scanParentApplicationContext = false;

    /**
     * A map of GWT RPC service's RemoteServiceRelativePath values and corresponding service spring beans.
     */
    private Map<String, Object> gwtRpcServiceMap = new HashMap<String, Object>();

    @Override
    public void afterPropertiesSet() throws Exception
    {
        LOG.debug("Scanning for GWT-RPC services.");
        setGwtRpcServiceMap(initGwtRpcServiceMap(applicationContext));
    }

    /**
     * Recursively scan the parent application contexts for annotated beans to publish. Beans from applications contexts
     * that are lower in the hierarchy overwrite beans found in parent application contexts.
     *
     * @param scanParentApplicationContext Defaults to <code>false</code>
     */
    public void setScanParentApplicationContext(boolean scanParentApplicationContext)
    {
        this.scanParentApplicationContext = scanParentApplicationContext;
    }

    /**
     * Scans the application context and its parents for service beans that implement the {@link
     * com.google.gwt.user.client.rpc.RemoteService}
     *
     * @param appContext Application context
     *
     * @return Map<String, Object> A map of RemoteServiceRelativePath annotation values vs the RPC service instances
     */
    private Map<String, Object> initGwtRpcServiceMap(final ApplicationContext appContext)
    {
        // Create a map of rpc services keyed against the RemoteServiceRelativePath annotation value
        Map<String, Object> rpcServiceMap = new HashMap<String, Object>();

        // If Gwt RPC service beans exist already (may be explicitly configured through spring config xml file)
        // then add them first.
        Map<String, Object> existingGwtRpcServiceMap = getGwtRpcServiceMap();
        if (existingGwtRpcServiceMap != null)
        {
            rpcServiceMap.putAll(existingGwtRpcServiceMap);
        }

        if (appContext != null)
        {
            //Find the beans of type RemoteService
            String[] remoteServiceBeans = appContext.getBeanNamesForType(RemoteService.class);
            if (!ArrayUtils.isEmpty(remoteServiceBeans))
            {
                // If remoteServiceBeans are found then scan for Gwt Rpc beans
                scanForGwtRpcBeans(appContext, rpcServiceMap, remoteServiceBeans);
            }
        }
        return rpcServiceMap;
    }

    private void scanForGwtRpcBeans(ApplicationContext appContext, Map<String, Object> rpcServiceMap,
        String[] remoteServiceBeans)
    {
        //Iterate through the beans from the application context and scan for beans with RemoteServiceRelativePath
        for (String beanName : remoteServiceBeans)
        {
            Object service = appContext.getBean(beanName);
            if (service != null)
            {
                // Get the RemoteServiceRelativePath annotation on the class
                final RemoteServiceRelativePath servicePathAnnotation =
                    ReflectionUtils.findAnnotation(service.getClass(), RemoteServiceRelativePath.class);

                if (servicePathAnnotation != null)
                {
                    // This class is annotated with RemoteServiceRelativePath so treat it as a GWT RPC service bean
                    // and add corresponding entry to the map
                    addRpcServiceBeanMapping(rpcServiceMap, service, servicePathAnnotation);
                }
            }
        }
        if (scanParentApplicationContext)
        {
            // Call this method recursively with parent application context if parent
            // application context needs to be scanned.
            scanForGwtRpcBeans(appContext.getParent(), rpcServiceMap, remoteServiceBeans);
        }
    }

    private void addRpcServiceBeanMapping(Map<String, Object> rpcServiceMap, Object service,
        RemoteServiceRelativePath servicePathAnnotation)
    {
        //Get the service's relative path value from the annotation
        String servicePath = servicePathAnnotation.value();
        Class<?> serviceClass = service.getClass();

        if (servicePath == null)
        {
            // If no value is specified with the RemoteServiceRelativePath annotation then warn
            LOG.warn("RemoteServiceRelativePath value for [{}] is null. This service will be mapped to handle GWT RPC" +
                " calls with null servicePath. Are you sure you want to do that?", serviceClass);
        }
        if ("".equals(servicePath))
        {
            // If an empty string is specified with the RemoteServiceRelativePath annotation then warn
            LOG.warn("RemoteServiceRelativePath value for [{}] is an empty string. This service will be mapped to " +
                "handle GWT RPC calls with empty servicePath. Are you sure you want to do that?", serviceClass);
        }

        // Check to see if an entry for the current servicePath already exists
        if (rpcServiceMap.containsKey(servicePath))
        {
            // If mapping already exists for the servicePath then skip the entry.
            Object existingServiceBean = rpcServiceMap.get(servicePath);
            LOG.warn(
                "Duplicate [{}] mappings found for the GWT RPC service. Ignoring [{}]. The [{}] will be used for path" +
                    " [{}].", servicePath, serviceClass,
                (existingServiceBean == null ? "null" : existingServiceBean.getClass()), servicePath);
        }
        else
        {
            // Add an entry of the servicePath vs service to the GWT-RPC service map
            rpcServiceMap.put(servicePath, service);
        }
    }

    @Override
    public Object getGwtRpcService(PortletRequest portletRequest, PortletResponse portletResponse)
    {
        // Read servicePath parameter from the request
        String servicePath = portletRequest.getParameter("servicePath");

        // Get the gwt-rpc service from the map
        Object service = getGwtRpcServiceMap().get(servicePath);

        if (service == null)
        {
            LOG.error("Could not find any GWT-RPC service for servicePath [{}]", servicePath);
        }
        else
        {
            LOG.debug("Found GWT-RPC service [{}] for servicePath [{}]", service.getClass().getName(), servicePath);
        }
        return service;
    }

    public Map<String, Object> getGwtRpcServiceMap()
    {
        return gwtRpcServiceMap;
    }

    /**
     * Setter for gwtRpcServiceMap. This setter is not expected to be invoked explicitly other than by the Spring
     * container to inject explicitly configured values of gwtRpcServiceMap. It is not required to inject explicit
     * mapping values. The class will automatically scan for GWT-RPC beans and create this mappings based on
     * RemoteServiceRelativePath annotations.
     *
     * @param gwtRpcServiceMap
     */

    public void setGwtRpcServiceMap(Map<String, Object> gwtRpcServiceMap)
    {
        this.gwtRpcServiceMap = gwtRpcServiceMap;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }
}
