/**
 *
 */
package org.dhbw.mosbach.ai.pizzafactory.articleservice.service;

/**
 * Exception handler to wrap an {@link Exception} into an
 * {@link ServiceException}.
 *
 * @author Alexander.Auch
 *
 */
class ArticleServiceExceptionHandler
{
	@FunctionalInterface
	public static interface HandledExceptionAction<T>
	{
		T execute() throws Exception;
	}

	@FunctionalInterface
	public static interface HandledExceptionActionNoParam
	{
		void execute() throws Exception;
	}

	public static <T> T handle(HandledExceptionAction<T> action, String exceptionMessage) throws ServiceException
	{
		try
		{
			return action.execute();
		}
		catch (final Exception e)
		{
			throw new ServiceException(exceptionMessage, e);
		}
	}

	public static void handle(HandledExceptionActionNoParam action, String exceptionMessage)
			throws ServiceException
	{
		try
		{
			action.execute();
		}
		catch (final Exception e)
		{
			throw new ServiceException(exceptionMessage, e);
		}
	}

	public static <T> T handleFn(HandledExceptionAction<T> action, String methodName) throws ServiceException
	{
		return handle(action, String.format("Error invoking %s", methodName));
	}

	public static void handleFn(HandledExceptionActionNoParam action, String methodName) throws ServiceException
	{
		handle(action, String.format("Error invoking %s", methodName));
	}
}
