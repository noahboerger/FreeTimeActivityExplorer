package org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.weather;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.APINoResultException;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.Coordinates;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OpenWeatherMapAPI {
	private final static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

	// 60 Anfragen pro minute
	private final static String API_KEY = "8a4daa0b5a7a0b116b2ada5245b56d8d";

	private static HashMap<Coordinates, Long> lastUpdated = new HashMap<>();
	private static HashMap<Coordinates, WeatherData> puffer = new HashMap<>();

	public static WeatherData getWeathertoCoords(Coordinates coordinates) throws APINoResultException {

		if (lastUpdated.containsKey(coordinates)) {
			// update every 60 s
			if (System.currentTimeMillis() - lastUpdated.get(coordinates) < 60000) {
				return puffer.get(coordinates);
			}
		}

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
			throw new APINoResultException();
		} catch (IOException e) {
			System.err.println("Error connecting to OpenWeatherMap API" + e);
			throw new APINoResultException();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {
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
		} catch (JSONException e) {
			System.err.println("Unable to Parse Json to WeatherData");
			throw new APINoResultException();
		}
		lastUpdated.put(coordinates, System.currentTimeMillis());
		puffer.put(coordinates, returnWeatherData);
		return returnWeatherData;
	}

	public static double temperatureKelvinToCelsius(double temperatureInKelvin) {
		return temperatureInKelvin - 273.15;
	}
}
