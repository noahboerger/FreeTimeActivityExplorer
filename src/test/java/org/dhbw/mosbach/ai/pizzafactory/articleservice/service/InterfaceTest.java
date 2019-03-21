package org.dhbw.mosbach.ai.pizzafactory.articleservice.service;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.dhbw.mosbach.ai.pizzafactory.articleservice.model.Article;
import org.dhbw.mosbach.ai.pizzafactory.articleservice.model.Currency;
import org.dhbw.mosbach.ai.pizzafactory.articleservice.model.Ingredient;
import org.dhbw.mosbach.ai.pizzafactory.articleservice.model.Review;
import org.dhbw.mosbach.ai.pizzafactory.articleservice.testhelper.TestHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;


@RunWith(Arquillian.class)
public class InterfaceTest
{
	@Deployment(testable = true)
	public static WebArchive createDeployment()
	{
		final WebArchive war = TestHelper.createWholeBuild();

		// System.out.println(war.toString(true));

		return war;
	}

	private static final String PIZZA_NAME = "Pizza Whatever";

	@Inject
	private ArticleSoapService soapService;

	@Test
	@Transactional
	public void testArticleInterface() throws ServiceException
	{
		final Article article = new Article(PIZZA_NAME, 1337);

		article.setCurrency(Currency.EUR);

		final Ingredient ingredient = new Ingredient("Cheese", 100, 100);
		ingredient.setCurrency(Currency.Dollar);
		article.setIngredients(Lists.newArrayList(ingredient));

		final Review review1 = new Review(article, "Schmeckt super", 5);
		final Review review2 = new Review(article, "mehr Käse wäre gut", 3);
		article.setReviews(Lists.newArrayList(review1, review2));

		soapService.deleteAllArticles();
		soapService.addArticle(article);

		final Article articleFromInterface = soapService.getArticle(PIZZA_NAME);

		Assert.assertNotNull(articleFromInterface);
		Assert.assertTrue(PIZZA_NAME.equals(articleFromInterface.getName()));
		Assert.assertEquals(articleFromInterface.getCurrency(), Currency.EUR);
	}
}
