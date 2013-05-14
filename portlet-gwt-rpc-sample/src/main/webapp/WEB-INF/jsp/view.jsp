<%--
  Copyright 2013 the original author or authors.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
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
