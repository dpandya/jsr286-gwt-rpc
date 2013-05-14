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
