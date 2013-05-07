package com.dushyant.sample.shared.dto;

/**
 * A sample service exception
 */
public class SampleException extends Exception
{
    private static final long serialVersionUID = -836773625516534332L;

    public SampleException()
    {
        //no-arg constructor
    }

    public SampleException(String s)
    {
        super(s);
    }

    public SampleException(String s, Throwable throwable)
    {
        super(s, throwable);
    }
}
