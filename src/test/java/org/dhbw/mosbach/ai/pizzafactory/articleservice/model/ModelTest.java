/**
 *
 */
package org.dhbw.mosbach.ai.pizzafactory.articleservice.model;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.dhbw.mosbach.ai.pizzafactory.articleservice.dao.ArticleDao;
import org.dhbw.mosbach.ai.pizzafactory.articleservice.testhelper.TestHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander.Auch
 *
 */
@RunWith(Arquillian.class)
public class ModelTest
{
	private static final String PIZZA_NAME_MARGHERITA = "Pizza Margherita";

	@Deployment(testable = true)
	public static WebArchive createDeployment()
	{
		final WebArchive war = TestHelper.createWholeBuild();

		// System.out.println(war.toString(true));

		return war;
	}

	@Inject
	private ArticleDao articleDao;

	@Test
	@Transactional
	public void testPersistArticle()
	{
		final Article article = new Article();
		article.setName(PIZZA_NAME_MARGHERITA);
		article.setPrice(555);

		final Ingredient ingred1 = new Ingredient("Tomaten", 100, 100);
		final Ingredient ingred2 = new Ingredient("Käse", 100, 100);

		article.getIngredients().add(ingred1);
		article.getIngredients().add(ingred2);

		articleDao.deleteAll();
		assertEquals(0, articleDao.getAll().size());

		articleDao.persist(article);
		assertEquals(1, articleDao.getAll().size());

		final Article articleFromDb = articleDao.get(PIZZA_NAME_MARGHERITA);
		articleFromDb.setPrice(articleFromDb.getPrice() + 123);
		articleDao.merge(articleFromDb);

		final List<Article> resultList = articleDao.getAll();
		for (final Article a : resultList)
		{
			System.out.println(a.getName());
			assertEquals(a.getName(), PIZZA_NAME_MARGHERITA);
			assertEquals(a.getPrice(), 555 + 123);
		}

		assertEquals(resultList.size(), 1);
	}
}
