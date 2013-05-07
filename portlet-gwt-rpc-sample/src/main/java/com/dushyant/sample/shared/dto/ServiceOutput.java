package com.dushyant.sample.shared.dto;

import java.io.Serializable;

/**
 * A sample service output class.
 */
public class ServiceOutput implements Serializable
{
    private static final long serialVersionUID = 8756418898459180797L;

    private String msg;
    private ServiceInput serviceInput;

    public ServiceOutput()
    {
        // A no-arg constructor. Required for GWT RPC.
    }

    public ServiceOutput(String msg, ServiceInput serviceInput)
    {
        this.msg = msg;
        this.serviceInput = serviceInput;
    }

    @Override
    public String toString()
    {
        return "ServiceOutput{" +
            "msg='" + msg + '\'' +
            ", serviceInput=" + serviceInput +
            '}';
    }
}
