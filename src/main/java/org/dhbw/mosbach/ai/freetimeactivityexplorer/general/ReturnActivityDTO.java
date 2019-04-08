package org.dhbw.mosbach.ai.freetimeactivityexplorer.general;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.weather.WeatherData;

public class ReturnActivityDTO {

	private String status;

	private String cityName;
	private Coordinates cityLocation;
	private WeatherData weatherData;
	private Activity[] activity;

	public ReturnActivityDTO(String status, String cityName, Coordinates cityLocation, WeatherData weatherData,
			Activity[] activity) {
		this.status = status;
		this.cityName = cityName;
		this.cityLocation = cityLocation;
		this.weatherData = weatherData;
		this.activity = activity;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Coordinates getCityLocation() {
		return cityLocation;
	}

	public void setCityLocation(Coordinates cityLocation) {
		this.cityLocation = cityLocation;
	}

	public WeatherData getWeatherData() {
		return weatherData;
	}

	public void setWeatherData(WeatherData weatherData) {
		this.weatherData = weatherData;
	}

	public Activity[] getActivity() {
		return activity;
	}

	public void setActivity(Activity[] activity) {
		this.activity = activity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
