package com.dushyant.sample.shared.rpc;

import com.dushyant.sample.shared.dto.ServiceInput;
import com.dushyant.sample.shared.dto.ServiceOutput;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * An async version of the {@link SampleGwtRpcService}
 */
public interface SampleGwtRpcServiceAsync
{
    void doSomeStuff(ServiceInput input, AsyncCallback<ServiceOutput> async);
}
