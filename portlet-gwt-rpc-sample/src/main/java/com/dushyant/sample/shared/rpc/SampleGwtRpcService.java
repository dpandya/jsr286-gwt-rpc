package com.dushyant.sample.shared.rpc;

import com.dushyant.sample.shared.dto.SampleException;
import com.dushyant.sample.shared.dto.ServiceInput;
import com.dushyant.sample.shared.dto.ServiceOutput;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * A sample GWT RPC service
 */
// The value below should be the one that is used to form the service path using "liferay-portlet:resourceURL" tag on
// JSP
@RemoteServiceRelativePath("sample-gwt-rpc-service")
public interface SampleGwtRpcService extends RemoteService
{
    ServiceOutput doSomeStuff(ServiceInput input) throws SampleException;
}
