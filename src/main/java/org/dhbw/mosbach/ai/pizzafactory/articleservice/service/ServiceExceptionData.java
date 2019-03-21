/**
 *
 */
package org.dhbw.mosbach.ai.pizzafactory.articleservice.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * WebFault wrapper class for holding info.
 *
 * @author Alexander.Auch
 *
 */
@XmlRootElement
public class ServiceExceptionData
{
	private String message;
	private String cause;
	private String stackTrace;

	public ServiceExceptionData()
	{
		this("", "", "");
	}

	public ServiceExceptionData(String message, String cause, String stackTrace)
	{
		this.message = message;
		this.cause = cause;
		this.stackTrace = stackTrace;
	}

	public ServiceExceptionData(Throwable t)
	{
		this.message = t.getMessage();

		final Optional<Throwable> cause = Optional.ofNullable(t.getCause());
		this.cause = cause.map(tr -> tr.getClass().getName()).orElse("N/A");

		final ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try (PrintStream ps = new PrintStream(bos))
		{
			t.printStackTrace(ps);
		}

		this.stackTrace = bos.toString();
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public void setCause(String cause)
	{
		this.cause = cause;
	}

	public void setStackTrace(String stackTrace)
	{
		this.stackTrace = stackTrace;
	}

	public String getMessage()
	{
		return message;
	}

	public String getCause()
	{
		return cause;
	}

	public String getStackTrace()
	{
		return stackTrace;
	}
}
