package org.dhbw.mosbach.ai.freetimeactivityexplorer.process;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.Activity;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.coords.Coordinates;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.coords.MapQuestAPI;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.weather.OpenWeatherMapAPI;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.weather.WeatherData;

@Dependent
public class ActivityFinder {
	
	private final Logger logger = Logger.getLogger("root");

	public Activity[] findActivity(String village) {
		//TODO getKoordsToVillage
		Coordinates coords = MapQuestAPI.getCoordsToVillage(village);
		Activity[] foundActivity = {new Activity(coords.getLatitude(), coords.getLongitude(), village + " todo: find activities and filter them")};
		
		WeatherData weatherData = OpenWeatherMapAPI.getWeathertoCoords(coords);
		
		logger.log(Level.INFO, "Weather: " + weatherData);
		
		//TODO getActivitiesToKoords
		
		//TODO Filter Activities according to the Weather
		return foundActivity;
	}
}
