package org.dhbw.mosbach.ai.freetimeactivityexplorer.service;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Path;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.dao.SearchLabelDao;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.Activity;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.SearchLabel;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.process.ActivityFinder;

@Path("/freetimeactivityexplorer")
public class FreeTimeActivityFinderServiceImp implements IFreeTimeActivityFinderService {
	
	
	
	@Inject
	private SearchLabelDao searchLabelDao;
	
	@Inject
	private ActivityFinder activityFinder;
	
	private final Logger logger = Logger.getLogger("root");

	@Override
	public Activity[] findActivity(String village) {
		logger.log(Level.INFO, "Calling to findActivity" + village);
	return activityFinder.findActivity(village);
	}

	@Override
	public void addSearchPattern(SearchLabel searchPattern) {
		logger.log(Level.INFO, "Calling to addSearchPattern" + searchPattern);
		searchLabelDao.add(searchPattern);
		
	}

	@Override
	public SearchLabel[] getAllSearchLabels() {
		logger.log(Level.INFO, "Calling to getAllSearchLabels");
		final Collection<SearchLabel> allSearchLables = searchLabelDao.getAll();

		return allSearchLables.toArray(new SearchLabel[allSearchLables.size()]);
	}
}
