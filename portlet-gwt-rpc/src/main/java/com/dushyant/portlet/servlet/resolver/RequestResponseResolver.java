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
package com.dushyant.portlet.servlet.resolver;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A resolver to provide HTTP Servlet Request/Response based on the given Portlet Request/Response.
 *
 * @author Dushyant Pandya
 */
public interface RequestResponseResolver
{
    /**
     * Returns the underlying HttpServletRequest for the given PortletRequest
     *
     * @param portletRequest
     *
     * @return Underlying HttpServletRequest for the given PortletRequest
     */
    HttpServletRequest getHttpServletRequest(PortletRequest portletRequest);

    /**
     * Returns the underlying HttpServletResponse for the given PortletResponse
     *
     * @param portletResponse
     *
     * @return Underlying HttpServletResponse for the given PortletResponse
     */
    HttpServletResponse getHttpServletResponse(PortletResponse portletResponse);
}
