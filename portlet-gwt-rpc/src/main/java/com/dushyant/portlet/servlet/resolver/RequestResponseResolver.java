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
