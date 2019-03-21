package org.dhbw.mosbach.ai.pizzafactory.articleservice.service;

import static org.dhbw.mosbach.ai.pizzafactory.articleservice.service.ArticleServiceExceptionHandler.handleFn;

import java.util.Collection;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.annotation.XmlElement;

import org.dhbw.mosbach.ai.pizzafactory.articleservice.dao.ArticleDao;
import org.dhbw.mosbach.ai.pizzafactory.articleservice.model.Article;

@WebService
public class ArticleSoapService implements IArticleService
{
	@PersistenceContext
	protected EntityManager em;

	@Inject
	private ArticleDao articleDao;

	@WebMethod
	@WebResult(name = "articles")
	@Override
	public Collection<Article> getAllArticles() throws ServiceException
	{
		return handleFn(() -> articleDao.getAll(), "getAllArticles");
	}

	@WebMethod
	@WebResult(name = "article")
	@Override
	public Article getArticle(@XmlElement(required = true) @WebParam(name = "name") String name) throws ServiceException
	{
		return handleFn(() -> articleDao.get(name), "getArticle");
	}

	@WebMethod
	@Override
	public void addArticle(@XmlElement(required = true) @WebParam(name = "articleToAdd") Article article)
			throws ServiceException
	{
		handleFn(() ->
		{
			articleDao.add(article);
		}, "addArticle");
	}

	@WebMethod
	@Override
	public void deleteArticle(@XmlElement(required = true) @WebParam(name = "articleToDelete") Article article)
			throws ServiceException
	{
		@SuppressWarnings("boxing")
		final boolean success = handleFn(() -> articleDao.delete(article), "deleteArticle");

		if (!success)
		{
			throw new ServiceException(String.format("Could not delete Article %s", article));
		}
	}

	@WebMethod
	@Override
	public void changeArticle(@XmlElement(required = true) @WebParam(name = "articleToChange") Article article)
			throws ServiceException
	{
		@SuppressWarnings("boxing")
		final boolean success = handleFn(() -> articleDao.change(article), "changeArticle");

		if (!success)
		{
			throw new ServiceException(String.format("Could not change Article %s", article));
		}
	}

	@Override
	public void deleteAllArticles() throws ServiceException
	{
		handleFn(() -> articleDao.deleteAll(), "deleteAllArticles");
	}
}
