package org.dhbw.mosbach.ai.freetimeactivityexplorer.service;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Path;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.dao.SearchLabelDao;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.Activity;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.SearchLabel;

@Path("/freetimeactivityexplorer")
public class FreeTimeActivityFinderServiceImp implements IFreeTimeActivityFinderService {
	
	@Inject
	private SearchLabelDao searchLabelDao;

	@Override
	public Activity[] findActivity(String village) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSearchPattern(SearchLabel searchLabel) {
		searchLabelDao.add(searchLabel);
		
	}

	@Override
	public SearchLabel[] getAllSearchLabels() {
		final Collection<SearchLabel> allSearchLables = searchLabelDao.getAll();

		return allSearchLables.toArray(new SearchLabel[allSearchLables.size()]);
	}
}
