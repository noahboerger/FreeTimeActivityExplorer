package org.dhbw.mosbach.ai.pizzafactory.articleservice.service;

import javax.xml.ws.WebFault;

@WebFault
public class ServiceException extends Exception
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private ServiceExceptionData exceptionData;

	public ServiceException()
	{
		super();
	}

	public ServiceException(String message)
	{
		this(message, null);
	}

	public ServiceException(Throwable cause)
	{
		this("", cause);
	}

	public ServiceException(String message, Throwable cause)
	{
		super(message, cause);
		exceptionData = new ServiceExceptionData(this);
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
		exceptionData = new ServiceExceptionData(this);
	}

	public ServiceExceptionData getExceptionData()
	{
		return exceptionData;
	}
}
