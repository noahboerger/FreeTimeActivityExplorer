package org.dhbw.mosbach.ai.pizzafactory.articleservice.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.dhbw.mosbach.ai.pizzafactory.articleservice.model.Article;

public interface ArticleRestService
{
	@GET
	@Path("/all")
	@Produces(MediaType.TEXT_XML)
	Article[] getAllArticles();

	@GET
	@Path("/alljson")
	@Produces("text/json")
	Article[] getAllArticlesJson();

	@GET
	@Path("/{name}")
	@Produces(MediaType.TEXT_XML)
	Article getArticle(@PathParam("name") String name);

	@POST
	@Consumes(MediaType.TEXT_XML)
	void addArticle(Article article);

	@DELETE
	@Path("/{name}")
	Response deleteArticle(@PathParam("name") String name);

	@PUT
	@Consumes(MediaType.TEXT_XML)
	void changeArticle(Article article);
}