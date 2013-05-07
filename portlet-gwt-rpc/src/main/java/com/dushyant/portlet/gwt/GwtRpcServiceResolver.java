package com.dushyant.portlet.gwt;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * A GWT RPC resolver which is responsible for resolving a GWT RPC service based on the given portlet request and
 * response.
 *
 * @author Dushyant Pandya
 * @see PathBasedGwtRpcServiceResolver
 * @see GwtRpcDispatcherInterceptor
 */
public interface GwtRpcServiceResolver
{
    /**
     * Returns an instance of the GWT RPC service for the given portlet request and response objects.
     *
     * @param portletRequest Portlet request
     * @param portletResponse Portlet response
     *
     * @return An instance of the GWT RPC service
     */
    public Object getGwtRpcService(PortletRequest portletRequest, PortletResponse portletResponse);
}
