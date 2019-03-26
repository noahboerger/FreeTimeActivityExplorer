package org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.weather;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.Coordinates;
import org.json.JSONArray;
import org.json.JSONObject;

public class OpenWeatherMapAPI {
	private final static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

	// 60 Anfragen pro minute
	private final static String API_KEY = "8a4daa0b5a7a0b116b2ada5245b56d8d";

	public static WeatherData getWeathertoCoords(Coordinates coordinates) {
		WeatherData returnWeatherData = new WeatherData();

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();

		try {

			StringBuilder sb = new StringBuilder(BASE_URL);
			sb.append("?lat=");
			sb.append(coordinates.getLatitude());
			sb.append("&lon=");
			sb.append(coordinates.getLongitude());
			sb.append("&APPID=");
			sb.append(API_KEY);

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}

		} catch (MalformedURLException e) {
			System.err.println("Error processing OpenWeatherMap API URL" + e);
			return null;
		} catch (IOException e) {
			System.err.println("Error connecting to OpenWeatherMap API" + e);
			return null;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		JSONObject jsonObj = new JSONObject(jsonResults.toString());
		JSONObject jsonMain = jsonObj.getJSONObject("main");
		returnWeatherData.setTemperature(temperatureKelvinToCelsius(jsonMain.getDouble("temp")));

		JSONArray jsonWeather = jsonObj.getJSONArray("weather");
		JSONObject jsonWeatherObj = jsonWeather.getJSONObject(0);
		String mainWeatherString = jsonWeatherObj.getString("main").toLowerCase();

		if (mainWeatherString.contains("rain") || mainWeatherString.contains("snow")) {
			returnWeatherData.setRaining(true);
		} else {
			returnWeatherData.setRaining(false);
		}
		return returnWeatherData;
	}

	public static double temperatureKelvinToCelsius(double temperatureInKelvin) {
		return temperatureInKelvin - 273.15;
	}
}
