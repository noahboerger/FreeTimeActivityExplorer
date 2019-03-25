package org.dhbw.mosbach.ai.freetimeactivityexplorer.process;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.Activity;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.coords.MapQuestAPI;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.search.GooglePlacesAPI;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.search.Place;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.weather.OpenWeatherMapAPI;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.weather.WeatherData;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.Coordinates;

@Dependent
public class ActivityFinder {
	
	private final Logger logger = Logger.getLogger("root");

	public Activity[] findActivity(String village) {
		//TODO getKoordsToVillage
		Coordinates coords = MapQuestAPI.getCoordsToVillage(village);
		
		WeatherData weatherData = OpenWeatherMapAPI.getWeathertoCoords(coords);
		
		logger.log(Level.INFO, "Weather: " + weatherData);
		
		
		
		ArrayList<Place> foundPlaces = GooglePlacesAPI.search("fitness", coords.getLatitude(), coords.getLongitude(), 10000);
		Activity testActivity = new Activity(coords, village + " Temperature " + weatherData.getTemperature() + " todo: find activities and filter them", "Testaddress");
		
		Activity[] foundActivity = new Activity[foundPlaces.size() + 1];
		
		foundActivity[0] = testActivity;
		int i = 1;
		for(Place foundPlace : foundPlaces) {
			foundActivity[i] = new Activity(foundPlace.getCoordinates(),foundPlace.getName(), foundPlace.getAddress());
			i++;
		}
		//TODO getActivitiesToKoords
		
		//TODO Filter Activities according to the Weather
		return foundActivity;
	}
}
