package org.dhbw.mosbach.ai.freetimeactivityexplorer.process;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.coords.MapQuestAPI;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.search.GooglePlacesAPI;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.search.Place;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.weather.OpenWeatherMapAPI;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.weather.WeatherData;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.dao.SearchLabelDao;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.APINoResultException;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.Activity;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.Coordinates;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.ReturnActivityDTO;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.SearchLabel;

@Dependent
public class ActivityFinder {

	@Inject
	private SearchLabelDao searchLabelDao;

	private final int SEARCH_RADIUS = 10000;

	public ReturnActivityDTO findActivity(String village) {
		
		String status = "OK";
		
		Coordinates coords;
		try {
			coords = MapQuestAPI.getCoordsToVillage(village);
		} catch (APINoResultException e) {
			return new ReturnActivityDTO("ERROR", village, null, null,null);
		}

		WeatherData weatherData;
		try {
			weatherData = OpenWeatherMapAPI.getWeathertoCoords(coords);
		} catch (APINoResultException e) {
			return new ReturnActivityDTO("ERROR", village, coords, null, null);
		}
		List<SearchLabel> allSearchLabelList = searchLabelDao.getAll();

		List<Place> foundPlaces = new ArrayList<Place>();
		
		for (SearchLabel searchLabel : allSearchLabelList) {
			if (searchLabel.isSuitableWeather(weatherData)) {
				try {
					List<Place> actFoundPlaces = GooglePlacesAPI.search(searchLabel.getSearchLabel(), coords, SEARCH_RADIUS);
					foundPlaces.addAll(actFoundPlaces);
				} catch (APINoResultException e) {
					status = "ERROR";
					continue;
				}
			}
		}

		Activity[] foundActivity = new Activity[foundPlaces.size()];

		int i = 0;
		for (Place foundPlace : foundPlaces) {
			foundActivity[i] = new Activity(foundPlace.getCoordinates(), foundPlace.getName(), foundPlace.getRating(),
					foundPlace.getAddress(), foundPlace.getActivityType());
			i++;
		}
		ReturnActivityDTO returnActivityDTO = new ReturnActivityDTO(status, village, coords, weatherData, foundActivity);
		return returnActivityDTO;
	}
}
