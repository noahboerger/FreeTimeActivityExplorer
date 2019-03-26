package org.dhbw.mosbach.ai.freetimeactivityexplorer.process;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.Activity;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.SearchLabel;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.coords.MapQuestAPI;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.search.GooglePlacesAPI;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.search.Place;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.weather.OpenWeatherMapAPI;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.weather.WeatherData;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.dao.SearchLabelDao;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.Coordinates;

@Dependent
public class ActivityFinder {

	@Inject
	private SearchLabelDao searchLabelDao;

	private final int SEARCH_RADIUS = 10000;

	private final Logger logger = Logger.getLogger("root");

	public Activity[] findActivity(String village) {
		Coordinates coords = MapQuestAPI.getCoordsToVillage(village);

		WeatherData weatherData = OpenWeatherMapAPI.getWeathertoCoords(coords);

		logger.log(Level.INFO, "Weather: " + weatherData);

		ArrayList<Place> foundPlaces = new ArrayList<Place>();

		List<SearchLabel> allSearchLabelList = searchLabelDao.getAll();

		for (SearchLabel searchLabel : allSearchLabelList) {
			if (searchLabel.isSuitableWeather(weatherData)) {
				foundPlaces.addAll(GooglePlacesAPI.search(searchLabel.getSearchLabel(), coords.getLatitude(),
						coords.getLongitude(), SEARCH_RADIUS));
			}
		}

		Activity testActivity = new Activity(coords,
				village + " Temperature " + weatherData.getTemperature() + " todo: find activities and filter them",
				1.0, "Testaddress");

		Activity[] foundActivity = new Activity[foundPlaces.size() + 1];

		foundActivity[0] = testActivity;
		int i = 1;
		for (Place foundPlace : foundPlaces) {
			foundActivity[i] = new Activity(foundPlace.getCoordinates(), foundPlace.getName(), foundPlace.getRating(),
					foundPlace.getAddress());
			i++;
		}
		return foundActivity;
	}
}
