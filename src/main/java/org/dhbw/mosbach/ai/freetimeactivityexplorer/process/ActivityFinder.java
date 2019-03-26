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
		Coordinates coords = MapQuestAPI.getCoordsToVillage(village);

		WeatherData weatherData = OpenWeatherMapAPI.getWeathertoCoords(coords);

		ArrayList<Place> foundPlaces = new ArrayList<Place>();

		List<SearchLabel> allSearchLabelList = searchLabelDao.getAll();

		for (SearchLabel searchLabel : allSearchLabelList) {
			if (searchLabel.isSuitableWeather(weatherData)) {
				foundPlaces.addAll(GooglePlacesAPI.search(searchLabel.getSearchLabel(), coords.getLatitude(),
						coords.getLongitude(), SEARCH_RADIUS));
			}
		}

		Activity[] foundActivity = new Activity[foundPlaces.size()];

		int i = 0;
		for (Place foundPlace : foundPlaces) {
			foundActivity[i] = new Activity(foundPlace.getCoordinates(), foundPlace.getName(), foundPlace.getRating(),
					foundPlace.getAddress());
			i++;
		}
		ReturnActivityDTO returnActivityDTO = new ReturnActivityDTO(village, coords, weatherData, foundActivity);
		return returnActivityDTO;
	}
}
