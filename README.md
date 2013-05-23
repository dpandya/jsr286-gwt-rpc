GWT RPC in Portlets using JSR286 Resource Requests
========
Portlet spec 2 (JSR286) added a new type of portlet requests called _resource requests_. These were specifically introduced to support AJAX in Portal environments. To use GWT-RPC in portal environment, the GWT-RPC requests should be made as portal resource requests. This gives access to various _portal_ and _portlet_ related objects like **PortletRequest**, **PortletSession** etc in GWT-RPC service implementations.

portlet-gwt-rpc
--------
The **portlet-gwt-rpc** module provides capabilities to make GWT RPC calls over portlet resource requests. 

liferay-portlet-gwt-rpc
--------
The **liferay-portlet-gwt-rpc** provides capabilities to make GWT RPC calls in Liferay Portal environment. This module depends on the above mentioned more generic **portlet-gwt-rpc** module.

portlet-gwt-rpc-sample
--------
This is a very simple portlet using GWT-RPC in Liferay Portal Environment. This portlet has a _SampleGwtRpcService_. The service passes sample _ServiceInput_ object and receives a sample _ServiceOutut_ object back over GWT RPC. It demonstrates the usage of "portlet related objects" like _PortletRequest_ and _PortletSession_ etc in GWT RPC service implementation.  

Usage
========

Prerequisites/Dependencies
--------
1. The **portlet-gwt-rpc** is intended to be used in portal environments using **Spring Portlet MVC**. 
2. The module uses another open source library **[GWT-SL](https://code.google.com/p/gwt-sl/ "GWT-SL")**

Using _liferay-portlet-gwt-rpc_ in your Liferay portlet
--------
1. Clone this GIT repository
2. Build **portlet-gwt-rpc** and **liferay-portlet-gwt-rpc** in sequence using Maven or Gradle. The repo has both the _pom.xml_ as well as the _build.gradle_ files for the same
3. Add dependency on **liferay-portlet-gwt-rpc** in your portlet project
4. Create GWT RPC remote service interface (by extending **RemoteService** and annotating it with **@RemoteServiceRelativePath**) and corresponding implementation in normal way
5. On server side:
	1. Inject an instance of **GwtRpcDispatcherInterceptor** interceptor class to an instance of a handler mapping in your spring application context file as follows. 
    ```<bean name="defaultAnnotationHandlerMapping" 
			class="org.springframework.web.portlet.mvc.annotation.DefaultAnnotationHandlerMapping" 
			p:interceptors-ref="gwtRpcDispatcherInterceptor" />```	
	See **jsr286-gwt-rpc/portlet-gwt-rpc-sample/src/main/resources/portlet-spring-config.xml** for reference.
	2. Form portlet resource phase url and pass the following two portlet request parameters 1. **ajaxMode=rpc** and 2. **servicePath** matching the value of **RemoteServiceRelativePath** annotation of the service. You can use **liferay-portlet:resourceURL** JSP tag to do this. See **jsr286-gwt-rpc/portlet-gwt-rpc-sample/src/main/webapp/WEB-INF/jsp/view.jsp** for reference.

6. On client side, in GWT code:
	
	1. Read the portlet resource request url created in step 5.2 using a JSNI method in GWT layer.
	2. Override the default GWT-RPC service url as follows. `SomeGwtServiceAsync service = GWT.create(SomeGwtService.class);`. 
	See **getGwtRpcService()** method in **jsr286-gwt-rpc/portlet-gwt-rpc-sample/src/main/java/com/dushyant/sample/ui/gwt/GwtRpcPortletEntryPoint** for reference.

Using _portlet-gwt-rpc_ in portlets (for portals other than Liferay)
--------
1. Clone this GIT repository
2. Build **portlet-gwt-rpc** using Maven or Gradle. The repo has both the _pom.xml_ as well as the _build.gradle_ files for the same
3. Add dependency on **portlet-gwt-rpc** in your portlet project
4. Create a class implementing the interface **com.dushyant.portlet.servlet.resolver.RequestResponseResolver** for the portal env you are using
5. Create a spring bean of the above class and make it available in your spring application context
6. Follow steps from 4 to 6 from _Using _liferay-portlet-gwt-rpc_ in your Liferay portlet above.

