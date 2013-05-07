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
 */
@Controller
// The following annotation tells the Spring container to execute this controller for the VIEW mode of the Portlet.
// For more information on different Portlet Modes and Phases refer to JSR 286 at
// http://www.jcp.org/en/jsr/detail?id=286
// For more information on Annotation Based Spring Portlet MVC refer to Spring Reference Documentation
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

        // Return the name of the view. This will result into showing view.jsp page. The view name to actual view
        // mapping happens through a "org.springframework.web.servlet.ViewResolver". We are using
        // InternalResourceViewResolver implementation. An instance of InternalResourceViewResolver is configured in
        // spring context xml file.

        //Fix the following line to return proper view name
        return "view";
    }
}
