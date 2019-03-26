package org.dhbw.mosbach.ai.freetimeactivityexplorer.service;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Path;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.dao.SearchLabelDao;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.Activity;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.ReturnActivityDTO;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.SearchLabel;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.process.ActivityFinder;

@Path("/freetimeactivityexplorer")
public class FreeTimeActivityExplorerServiceImp implements IFreeTimeActivityExplorerService {

	@Inject
	private SearchLabelDao searchLabelDao;

	@Inject
	private ActivityFinder activityFinder;

	private final Logger logger = Logger.getLogger("root");

	@Override
	public ReturnActivityDTO findActivity(String village) {
		logger.log(Level.INFO, "Calling to findActivity in " + village);
		
		return activityFinder.findActivity(village);
	}

	@Override
	public void addSearchLabel(SearchLabel searchLabel) {
		logger.log(Level.INFO, "Calling to addSearchLabel: " + searchLabel);
		
		searchLabelDao.add(searchLabel);
	}

	@Override
	public SearchLabel[] getAllSearchLabels() {
		logger.log(Level.INFO, "Calling to getAllSearchLabels");
		
		final Collection<SearchLabel> allSearchLables = searchLabelDao.getAll();
		return allSearchLables.toArray(new SearchLabel[allSearchLables.size()]);
	}

	@Override
	public void initializeDataBase() {
		logger.log(Level.INFO, "Calling to initialzeDataBase");
		
		SearchLabel searchLabelFitnessstudio = new SearchLabel("Fitnessstuido", Float.MIN_VALUE, Float.MAX_VALUE, true);
		SearchLabel searchLabelFreibad = new SearchLabel("Freibad", 18.0, Float.MAX_VALUE, false);
		SearchLabel searchLabelHallenbad = new SearchLabel("Hallenbad", Float.MIN_VALUE, Float.MAX_VALUE, true);
		SearchLabel searchLabelTennis = new SearchLabel("Tennis", 14.0, 35.0, false);
		SearchLabel searchLabelFussballPlatz = new SearchLabel("FussballPlatz", 12.0, 35.0, false);

		List<SearchLabel> searchLabelList = searchLabelDao.getAll();

		if (!searchLabelList.contains(searchLabelFitnessstudio)) {
			searchLabelDao.add(searchLabelFitnessstudio);
		}
		if (!searchLabelList.contains(searchLabelFreibad)) {
			searchLabelDao.add(searchLabelFreibad);
		}
		if (!searchLabelList.contains(searchLabelHallenbad)) {
			searchLabelDao.add(searchLabelHallenbad);
		}
		if (!searchLabelList.contains(searchLabelTennis)) {
			searchLabelDao.add(searchLabelTennis);
		}
		if(!searchLabelList.contains(searchLabelFussballPlatz)) {
			searchLabelDao.add(searchLabelFussballPlatz);
		}
	}
}
