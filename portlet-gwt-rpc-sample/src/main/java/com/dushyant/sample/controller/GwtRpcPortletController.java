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
package com.dushyant.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A controller class for this Portlet. The controller in this sample does not do anything other than returning to the
 * view.jsp. You may have some interesting stuff happening in this controller to populate initial "stuff" in your JSP
 * file.
 *
 * @author Dushyant Pandya
 */
@Controller
// The following annotation tells the Spring container to execute this controller for the VIEW mode of the Portlet.
// For more information on different Portlet Modes and Phases refer to JSR 286 at
// http://www.jcp.org/en/jsr/detail?id=286
// For more information on Annotation Based Spring Portlet MVC refer to Spring Reference Documentation
// http://static.springsource.org/spring/docs/3.2.x/spring-framework-reference/html/
@RequestMapping("VIEW")
public class GwtRpcPortletController
{
    private static final Logger LOG = LoggerFactory.getLogger(GwtRpcPortletController.class);

    /**
     * A method to handle the Portlet's render phase.
     */
    @RequestMapping
    public String view(Model model)
    {
        LOG.debug("Portlet render phase executed.");
        return "view";
    }
}
