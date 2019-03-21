/**
 *
 */
package org.dhbw.mosbach.ai.pizzafactory.articleservice.service;

import java.net.URISyntaxException;
import java.net.URL;

import org.dhbw.mosbach.ai.pizzafactory.articleservice.model.Article;
import org.dhbw.mosbach.ai.pizzafactory.articleservice.testhelper.TestHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander.Auch
 *
 */
@RunWith(Arquillian.class)
@RunAsClient
public class ArticleRestTest
{
	private static final String ARTICLE_NAME = "Pizza Undercover";
	private static final int ARTICLE_PRICE = 500;

	@Deployment(testable = false)
	public static WebArchive createDeployment()
	{
		return TestHelper.createWholeBuild();
	}

	@ArquillianResource
	private URL url;

	@Test
	public void testService() throws NullPointerException, URISyntaxException
	{
		// using Resteasy
		// resteasy-jaxb-provider maven package is needed

		final ResteasyClient client = new ResteasyClientBuilder().build();
		final ResteasyWebTarget target = client.target(url.toURI().resolve("/rest/article"));

		// Use ArticleRestService interface to automatically generate a proxy object
		// which calls the REST service
		final ArticleRestService restService = target.proxy(ArticleRestService.class);

		restService.deleteArticle(ARTICLE_NAME);
		Assert.assertNull(restService.getArticle(ARTICLE_NAME));

		final Article article = new Article(ARTICLE_NAME, ARTICLE_PRICE);
		restService.addArticle(article);

		final Article articleFromService = restService.getArticle(ARTICLE_NAME);
		Assert.assertNotNull(articleFromService);
		Assert.assertEquals(ARTICLE_NAME, articleFromService.getName());
		Assert.assertEquals(ARTICLE_PRICE, articleFromService.getPrice());

		// Delete article again
		restService.deleteArticle(ARTICLE_NAME);
		Assert.assertNull(restService.getArticle(ARTICLE_NAME));
	}
}
