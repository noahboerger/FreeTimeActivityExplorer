package org.dhbw.mosbach.ai.pizzafactory.articleservice.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.dhbw.mosbach.ai.pizzafactory.articleservice.model.Article;
import org.dhbw.mosbach.ai.pizzafactory.articleservice.model.Ingredient;
import org.dhbw.mosbach.ai.pizzafactory.articleservice.model.Review;
import org.dhbw.mosbach.ai.pizzafactory.db.BaseDao;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@Dependent
public class ArticleDao extends BaseDao<Article, Long, String>
{
	/**
	 *
	 */
	private static final long serialVersionUID = -5385253294954522663L;

	private final Logger logger = Logger.getLogger("root");

	@Inject
	private IngredientDao ingredientDao;

	public ArticleDao()
	{
		super();
	}

	@Override
	public List<Article> getAll()
	{
		logger.log(Level.INFO, "Call to getAllArticles");

		return ImmutableList.copyOf(super.getAll());
	}

	@Override
	public Article get(String name)
	{
		logger.log(Level.INFO, "Call to getArticle({0})", name);

		if (name == null)
		{
			return null;
		}

		final List<Article> resultList = em.createQuery("from Article a where a.name = :name", Article.class)
				.setParameter("name", name).getResultList();

		return resultList.isEmpty() ? null : resultList.iterator().next();
	}

	@Override
	@Transactional
	public boolean add(final Article article)
	{
		logger.log(Level.INFO, "Call to addArticle({0})", article);

		// using a lambda expression
		try
		{
			updateManyToOneRelations(article);
			updateIngredients(article);

			persist(article);
		}
		catch (final Exception e)
		{
			return false;
		}

		return true;
	}

	/**
	 * Update ingredients, i.e., make a db lookup to see whether the ingredient
	 * already exists.
	 *
	 * @param article
	 *          article
	 */
	private void updateIngredients(Article article)
	{
		if (article.getIngredients() == null)
			return;

		final List<Ingredient> dbIngredients = Lists.newArrayListWithCapacity(article.getIngredients().size());

		for (final Ingredient ingredient : article.getIngredients())
		{
			final Ingredient dbIngredient = ingredientDao.get(ingredient.getName());

			// put either db ingredient, or the new ingredient into the target list
			dbIngredients.add(dbIngredient != null ? dbIngredient : ingredient);
		}

		article.setIngredients(dbIngredients);
	}

	/**
	 * Updates relations between dependent manytoone objects and the given
	 * article.
	 *
	 * @param article
	 *          article
	 */
	private void updateManyToOneRelations(Article article)
	{
		if (article.getReviews() != null)
		{
			for (final Review review : article.getReviews())
			{
				review.setArticle(article);
			}
		}
	}

	@Transactional
	public void deleteAll()
	{
		logger.log(Level.INFO, "Call to deleteAll()");

		em.createQuery("DELETE FROM Review r").executeUpdate();
		em.createQuery("DELETE FROM Article a").executeUpdate();
	}

	@Override
	@Transactional
	public boolean delete(final Article article)
	{
		logger.log(Level.INFO, "Call to deleteArticle({0})", article);

		return (article != null) ? deleteByKey(article.getName()) : false;
	}

	@Override
	@Transactional
	public boolean deleteByKey(String key)
	{
		logger.log(Level.INFO, "Call to deleteByKey({0})", key);

		if (key != null)
		{
			em.createQuery("DELETE FROM Review r WHERE r.article.name = :name").setParameter("name", key).executeUpdate();

			final int updateNum = em.createQuery("DELETE FROM Article a WHERE a.name = :name").setParameter("name", key)
					.executeUpdate();

			return updateNum > 0;
		}

		return false;
	}

	@Override
	@Transactional
	public boolean change(final Article article)
	{
		logger.log(Level.INFO, "Call to changeArticle({0})", article);

		// TODO interface is broken because the name of the article cannot be
		// changed in that way!


		if ((article != null) && (article.getName() != null))
		{
			// final Article managedArticle = get(article.getName());

			// TODO dirty hack
			delete(article);
			add(article);
		}

		return true;
	}
}
