package com.dushyant.sample.shared.dto;

import java.io.Serializable;

/**
 * A sample service input parameter class.
 */
public class ServiceInput implements Serializable
{
    private static final long serialVersionUID = -3545361501053455276L;

    private String name;

    public ServiceInput()
    {
        // A no-arg constructor. Required for GWT RPC.
    }

    public ServiceInput(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return "ServiceInput{" +
            "name='" + name + '\'' +
            '}';
    }
}
