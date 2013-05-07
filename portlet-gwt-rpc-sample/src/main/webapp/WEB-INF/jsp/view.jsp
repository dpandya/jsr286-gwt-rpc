<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" isELIgnored="false" %>
<liferay-theme:defineObjects/>

<%
   //Get the instance ID of of this portlet. The following portletDisplay object is initalized by the
   // liferay-theme:defineObjects tag above.
   String portletId = portletDisplay.getId();
%>

<liferay-portlet:resourceURL var="sampleServiceUrl" portletName="<%=portletId%>">
   <liferay-portlet:param name="servicePath" value="sample-gwt-rpc-service"/>
   <liferay-portlet:param name="ajaxMode" value="rpc"/>
</liferay-portlet:resourceURL>

<script type="text/javascript" language="javascript">
   gwtRpcURL = "<%=sampleServiceUrl%>";
</script>

<%-- The following Div is populated through GWT --%>
<div id="gwtRpcPortlet"></div>
</a>
