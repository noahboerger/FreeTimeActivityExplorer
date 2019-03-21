package org.dhbw.mosbach.ai.pizzafactory.articleservice.service;
/**
 *
 */


import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.dhbw.mosbach.ai.pizzafactory.articleservice.model.Article;
import org.dhbw.mosbach.ai.pizzafactory.articleservice.testhelper.ClientWrapper;
import org.dhbw.mosbach.ai.pizzafactory.articleservice.testhelper.TestHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander.Auch
 *
 */
@RunWith(Arquillian.class)
// @DefaultDeployment
@RunAsClient
public class ArticleServiceTest
{
	@Deployment(testable = false)
	public static WebArchive createDeployment()
	{
		final WebArchive war = TestHelper.createWholeBuild();

		// System.out.println(war.toString(true));

		return war;
	}

	@ArquillianResource
	private URL url;

	@Test
	public void testSoapService() throws Exception
	{
		// use Apache CXF dynamic client
		// see http://cxf.apache.org/docs/dynamic-clients.html
		final JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		final Client client = dcf.createClient(getWsdlUrl());

		final ClientWrapper clientWrapper = new ClientWrapper(client);

		final Article article = new Article("Pizza Whatever", 1337);

		// does not work yet :-(
		// final Ingredient ingredient = new Ingredient("Cheese", 100, 100);
		// article.setIngredients(Lists.newArrayList(ingredient));

		// first, delete all articles
		clientWrapper.invoke("deleteAllArticles");

		final Object[] emptyArticles = clientWrapper.invoke("getAllArticles");

		// ensure that no articles are in the database
		Assert.assertTrue(emptyArticles.length == 1 && emptyArticles[0] instanceof Collection);
		Assert.assertTrue(((Collection<?>) emptyArticles[0]).isEmpty());

		// add our pizza
		clientWrapper.invoke("addArticle", article);

		final Object[] pizzaArticleResponse = clientWrapper.invoke("getArticle", article.getName());

		Assert.assertTrue(pizzaArticleResponse.length == 1);
		final Article pizzaArticle = new Article();
		BeanUtils.copyProperties(pizzaArticle, pizzaArticleResponse[0]);

		Assert.assertEquals(pizzaArticle.getName(), article.getName());
		Assert.assertEquals(pizzaArticle.getPrice(), article.getPrice());
	}

	private URL getWsdlUrl()
	{
		try
		{
			return url.toURI().resolve("ArticleSoapService?wsdl").toURL();
		}
		catch (final MalformedURLException | URISyntaxException e)
		{
			throw new RuntimeException(e);
		}
	}
}
