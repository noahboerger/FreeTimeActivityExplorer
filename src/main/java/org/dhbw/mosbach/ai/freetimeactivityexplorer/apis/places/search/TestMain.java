package org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.search;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.coords.MapQuestAPI;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.weather.OpenWeatherMapAPI;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.Coordinates;

public class TestMain {
	public static void main(String args[]) {
		 System.out.println(GooglePlacesAPI.search("fitness", 49.633741, 9.649268, 10000));
		// MapQuestAPI.getCoordsToVillage("Berlin");
		//OpenWeatherMapAPI.getWeathertoCoords(new Coordinates(49.7921162, 9.1590714));
	}
}