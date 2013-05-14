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
package com.dushyant.sample.ui.gwt;

import com.dushyant.sample.shared.dto.ServiceInput;
import com.dushyant.sample.shared.dto.ServiceOutput;
import com.dushyant.sample.shared.rpc.SampleGwtRpcService;
import com.dushyant.sample.shared.rpc.SampleGwtRpcServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * The Entry Point class for GWT.
 *
 * @author Dushyant Pandya
 */
public class GwtRpcPortletEntryPoint implements EntryPoint
{
    private SampleGwtRpcServiceAsync service = null;

    /**
     * This method is invoked when GWT Module is loaded on the page
     */
    public void onModuleLoad()
    {
        // This is just a sample entry point method for this portlet. In real world you would probably use MVP
        // pattern with UiBinder files instead of adding stuff to a div like this and directly making RPC calls from
        // here.
        // The "gwtRpcPortlet" here is the ID of an empty div used in "view.jsp" file
        RootPanel mainPanel = RootPanel.get("gwtRpcPortlet");

        final TextBox inputName = new TextBox();
        mainPanel.add(inputName);

        Button serviceCallButton = new Button("Make GWT RPC Call");
        mainPanel.add(serviceCallButton);

        serviceCallButton.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                ServiceInput serviceInput = new ServiceInput(inputName.getText());
                // Making the RPC call here
                getGwtRpcService().doSomeStuff(serviceInput, new AsyncCallback<ServiceOutput>()
                {
                    @Override
                    public void onFailure(Throwable caught)
                    {
                        Window.alert("Service call failed " + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(ServiceOutput result)
                    {
                        Window.alert("Successfully called GWT RPC service. Server says ---- " + result);
                    }
                });
            }
        });
    }

    private SampleGwtRpcServiceAsync getGwtRpcService()
    {
        if (service == null)
        {
            service = GWT.create(SampleGwtRpcService.class);

            // Change the default URL of the service to go through portlet's resource phase
            String referenceDataServiceUrl = getServiceUrl();
            ((ServiceDefTarget) service).setServiceEntryPoint(referenceDataServiceUrl);
        }
        return service;
    }

    /**
     * JSNI method to read gwt rpc service url from the main.js file.
     *
     * @return The GWT RPC service url as formed using "liferay-portlet:resourceURL" tag.
     */
    public static native String getServiceUrl()
        /*-{
            return $wnd.gwtRpcURL;
        }-*/;
}
