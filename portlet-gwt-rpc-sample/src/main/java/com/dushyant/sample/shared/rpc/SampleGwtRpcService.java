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

import com.dushyant.sample.shared.dto.SampleException;
import com.dushyant.sample.shared.dto.ServiceInput;
import com.dushyant.sample.shared.dto.ServiceOutput;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * A sample GWT RPC service
 *
 * @author Dushyant Pandya
 */
// The value below should be the one that is used to form the service path using "liferay-portlet:resourceURL" tag on
// JSP
@RemoteServiceRelativePath("sample-gwt-rpc-service")
public interface SampleGwtRpcService extends RemoteService
{
    ServiceOutput doSomeStuff(ServiceInput input) throws SampleException;
}
