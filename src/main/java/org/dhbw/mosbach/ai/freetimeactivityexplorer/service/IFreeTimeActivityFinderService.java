package org.dhbw.mosbach.ai.freetimeactivityexplorer.service;

import java.util.Collection;

import javax.jws.WebService;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.Activity;



@WebService(name = "freeTimeActivityFinder")
public interface IFreeTimeActivityFinderService {

	Collection<Activity> findActivity(String village);
	
	void addSearchPattern(String searchPattern, double tempMin, double tempMax);
	
	String getAllSearchPatterns();
}
