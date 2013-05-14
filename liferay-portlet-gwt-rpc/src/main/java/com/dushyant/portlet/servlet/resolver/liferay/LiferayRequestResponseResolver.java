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
 *
 * @author Dushyant Pandya
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
