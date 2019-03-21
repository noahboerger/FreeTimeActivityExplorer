package org.dhbw.mosbach.ai.pizzafactory.articleservice.service;

import java.util.Collection;

import org.dhbw.mosbach.ai.pizzafactory.articleservice.model.Article;

public interface IArticleService
{
	/**
	 * @return a list of all articles
	 */
	Collection<Article> getAllArticles() throws ServiceException;

	/**
	 * Return single article by name.
	 *
	 * @param name
	 *          name of article
	 * @return article or null if none found
	 */
	Article getArticle(String name) throws ServiceException;

	/**
	 * Adds an article to the database.
	 *
	 * @param article
	 */
	void addArticle(Article article) throws ServiceException;

	/**
	 * Deletes an article.
	 *
	 * @param article
	 */
	void deleteArticle(Article article) throws ServiceException;

	/**
	 * Changes attributes of an article. When using this function, it is not
	 * possible to change the name of an article, since the name will be used as a
	 * key for database lookup. For changing a name, the original article has to
	 * be deleted, and re-created from scratch using the new name.
	 *
	 * @param article
	 * @throws ServiceException
	 */
	void changeArticle(Article article) throws ServiceException;

	/**
	 * Deletes all articles.
	 * 
	 * @throws ServiceException
	 */
	void deleteAllArticles() throws ServiceException;
}
