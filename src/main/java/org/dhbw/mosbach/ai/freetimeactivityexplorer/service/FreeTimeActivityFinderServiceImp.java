package org.dhbw.mosbach.ai.freetimeactivityexplorer.service;

import java.util.Collection;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.dao.SearchLabelDao;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.Activity;

@WebService(endpointInterface = "org.dhbw.mosbach.ai.service.IFreeTimeActivityFinder", serviceName = "freeTimeActivityFinderService")
public class FreeTimeActivityFinderServiceImp implements IFreeTimeActivityFinderService {
	
	@Inject
	private SearchLabelDao searchLabelDao;

	@WebMethod
	@WebResult(name = "activities")
	@Override
	public Collection<Activity> findActivity(@WebParam(name = "locationVillage") String village) {
		// TODO Auto-generated method stub
		return null;
	}

	@WebMethod
	@Override
	public void addSearchPattern(@WebParam(name = "activityPattern") String searchPattern, @WebParam(name = "minimumTemperature") double tempMin,@WebParam(name = "maximumTemperature") double tempMax) {
		// TODO Auto-generated method stub
		
	}

	@WebMethod
	@WebResult(name = "searchPatterns")
	@Override
	public String getAllSearchPatterns() {
		// TODO Auto-generated method stub
		return null;
	}

}
