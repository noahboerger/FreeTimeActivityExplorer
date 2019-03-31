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
	public String initializeDataBase() {
		logger.log(Level.INFO, "Calling to initialzeDataBase");

		SearchLabel searchLabelFitnessstudio = new SearchLabel("Fitnessstudio", Float.MIN_VALUE, Float.MAX_VALUE, true);
		SearchLabel searchLabelFreibad = new SearchLabel("Freibad", 18.0, Float.MAX_VALUE, false);
		SearchLabel searchLabelHallenbad = new SearchLabel("Hallenbad", Float.MIN_VALUE, Float.MAX_VALUE, true);
		SearchLabel searchLabelTennis = new SearchLabel("Tennis", 14.0, 35.0, false);
		SearchLabel searchLabelFussball = new SearchLabel("Fussball", 12.0, 35.0, false);
		SearchLabel searchLabelKegeln = new SearchLabel("Kegeln", Float.MIN_VALUE, Float.MAX_VALUE, true);
		SearchLabel searchLabelBowling = new SearchLabel("Bowling", Float.MIN_VALUE, Float.MAX_VALUE, true);
		SearchLabel searchLabelBibliothek = new SearchLabel("Bibliothek", Float.MIN_VALUE, Float.MAX_VALUE, true);
		SearchLabel searchLabelKlettern = new SearchLabel("Klettern", 12.0, 30.0, false);
		SearchLabel searchLabelSkatePark = new SearchLabel("Skatepark", 10.0, 30.0, false);
		SearchLabel searchLabelGolf = new SearchLabel("Golf", 10.0, 35.0, false);

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
		if (!searchLabelList.contains(searchLabelFussball)) {
			searchLabelDao.add(searchLabelFussball);
		}
		if (!searchLabelList.contains(searchLabelKegeln)) {
			searchLabelDao.add(searchLabelKegeln);
		}
		if (!searchLabelList.contains(searchLabelBowling)) {
			searchLabelDao.add(searchLabelBowling);
		}
		if (!searchLabelList.contains(searchLabelBibliothek)) {
			searchLabelDao.add(searchLabelBibliothek);
		}
		if (!searchLabelList.contains(searchLabelKlettern)) {
			searchLabelDao.add(searchLabelKlettern);
		}
		if (!searchLabelList.contains(searchLabelSkatePark)) {
			searchLabelDao.add(searchLabelSkatePark);
		}
		if(!searchLabelList.contains(searchLabelGolf)) {
			searchLabelDao.add(searchLabelGolf);
		}
		return "Initialized DataBase with some Standard activities - you can add some more!";
	}

	@Override
	public String deleteSearchLabel(String name) {
		for (SearchLabel searchlabel : searchLabelDao.getAll()) {
			if (searchlabel.getSearchLabel().equalsIgnoreCase(name)) {
				searchLabelDao.delete(searchlabel);
				return "Deleted";
			}
		}
		return "Nothing found to delete";
	}
}
