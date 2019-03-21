package org.dhbw.mosbach.ai.pizzafactory.articleservice.service;

import java.util.Collection;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.dhbw.mosbach.ai.pizzafactory.articleservice.dao.ArticleDao;
import org.dhbw.mosbach.ai.pizzafactory.articleservice.model.Article;

@Path("/article")
public class ArticleRestServiceImpl implements ArticleRestService
{
	private final Logger logger = Logger.getLogger("root");

	@Inject
	private ArticleDao articleDao;

	@Override
	public Article[] getAllArticles()
	{
		// encapsulate generic type info into own inner class
		/*
		 * final GenericEntity<List<Article>> genericEntity = new
		 * GenericEntity<List<Article>>( new
		 * ArrayList<Article>(articleDao.getAllArticles())) { };
		 *
		 * return Response.ok(genericEntity).build();
		 */

		final Collection<Article> allArticles = articleDao.getAll();

		return allArticles.toArray(new Article[allArticles.size()]);
	}

	@Override
	public Article[] getAllArticlesJson()
	{
		final Collection<Article> allArticles = articleDao.getAll();

		return allArticles.toArray(new Article[allArticles.size()]);
	}

	@Override
	public Article getArticle(String name)
	{
		return articleDao.get(name);
	}

	@Override
	public void addArticle(Article article)
	{
		articleDao.add(article);
	}

	@Override
	public Response deleteArticle(String name)
	{
		try
		{
			logger.info(String.format("Deleting Article '%s'", name));

			final Article article = getArticle(name);
			final boolean success = articleDao.delete(article);

			if (success)
				return Response.status(Status.OK).build();
			else
				return Response.serverError().entity("Record not found").build();

		} catch (final Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@Override
	public void changeArticle(Article article)
	{
		articleDao.change(article);
	}
}
