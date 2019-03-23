package org.dhbw.mosbach.ai.freetimeactivityexplorer.process;

import javax.enterprise.context.Dependent;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.Activity;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.places.coords.Coordinates;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.places.coords.CoordsToVillage;

@Dependent
public class ActivityFinder {

	public Activity[] findActivity(String village) {
		//TODO getKoordsToVillage
		Coordinates coords = CoordsToVillage.getCoordsToVillage(village);
		Activity[] foundActivity = {new Activity(coords.getLatitude(), coords.getLongitude(), village + " todo: find activities and filter them")};
		
		//TODO getWeatherToKoords
		
		//TODO getActivitiesToKoords
		
		//TODO Filter Activities according to the Weather
		return foundActivity;
	}
}
