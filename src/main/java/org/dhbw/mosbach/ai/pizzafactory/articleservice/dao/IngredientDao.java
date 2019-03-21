package org.dhbw.mosbach.ai.pizzafactory.articleservice.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;

import org.dhbw.mosbach.ai.pizzafactory.articleservice.model.Ingredient;
import org.dhbw.mosbach.ai.pizzafactory.db.BaseDao;

@Dependent
public class IngredientDao extends BaseDao<Ingredient, Long, String>
{
	/**
	 *
	 */
	private static final long serialVersionUID = 7535800010653286921L;

	private final Logger logger = Logger.getLogger("root");

	public IngredientDao()
	{
		super();
	}

	@Override
	public Ingredient get(String name)
	{
		logger.log(Level.INFO, "Call to get({0})", name);

		if (name == null)
		{
			return null;
		}

		final List<Ingredient> resultList = em.createQuery("from Ingredient i where i.name = :name", Ingredient.class)
				.setParameter("name", name).getResultList();

		return resultList.isEmpty() ? null : resultList.iterator().next();
	}

	@Override
	public boolean add(Ingredient ingredient)
	{
		logger.log(Level.INFO, "Call to add({0})", ingredient);

		try
		{
			persist(ingredient);
		} catch (final Exception e)
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean change(Ingredient entity)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Ingredient entity)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteByKey(String key)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
