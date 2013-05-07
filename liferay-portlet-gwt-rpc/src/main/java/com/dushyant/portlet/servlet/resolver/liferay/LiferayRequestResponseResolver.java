package com.dushyant.portlet.servlet.resolver.liferay;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dushyant.portlet.servlet.resolver.RequestResponseResolver;
import com.liferay.portal.util.PortalUtil;
import org.springframework.stereotype.Component;

/**
 * Liferay specific implementation of the {@link RequestResponseResolver}
 */
@Component
public class LiferayRequestResponseResolver implements RequestResponseResolver
{
    @Override
    public HttpServletRequest getHttpServletRequest(PortletRequest portletRequest)
    {
        return PortalUtil.getHttpServletRequest(portletRequest);
    }

    @Override
    public HttpServletResponse getHttpServletResponse(PortletResponse portletResponse)
    {
        return PortalUtil.getHttpServletResponse(portletResponse);
    }
}
