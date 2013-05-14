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
package com.dushyant.sample.shared.rpc;

import com.dushyant.sample.shared.dto.ServiceInput;
import com.dushyant.sample.shared.dto.ServiceOutput;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * An async version of the {@link SampleGwtRpcService}
 *
 * @author Dushyant Pandya
 */
public interface SampleGwtRpcServiceAsync
{
    void doSomeStuff(ServiceInput input, AsyncCallback<ServiceOutput> async);
}
