package org.dhbw.mosbach.ai.freetimeactivityexplorer.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.ReturnActivityDTO;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.SearchLabel;

public interface IFreeTimeActivityExplorerService {

	@GET
	@Path("/findactivity/{village}")
	@Produces(MediaType.APPLICATION_JSON)
	ReturnActivityDTO findActivity(@PathParam("village") String village);

	@POST
	@Consumes(MediaType.TEXT_XML)
	void addSearchLabel(SearchLabel searchLabel);

	@GET
	@Path("/allsearchlabels")
	@Produces(MediaType.APPLICATION_JSON)
	SearchLabel[] getAllSearchLabels();

	@GET
	@Path("/initializedb")
	@Produces(MediaType.TEXT_HTML)
	String initializeDataBase();

	@GET
	@Path("/deletesearchlabel/{name}")
	@Produces(MediaType.TEXT_HTML)
	String deleteSearchLabel(@PathParam("name") String name);
}
