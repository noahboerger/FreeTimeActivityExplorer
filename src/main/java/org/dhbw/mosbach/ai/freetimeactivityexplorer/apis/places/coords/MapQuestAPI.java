package org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.coords;

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

public class MapQuestAPI {

	private final static String BASE_URL = "http://www.mapquestapi.com/geocoding/v1/address";

	// 15 000 Anfragen frei
	private final static String API_KEY = "F5JOdf5ICpDl13pWwomhONGeJJdfHRtZ";

	private static HashMap<String, Coordinates> puffer = new HashMap<>();

	public static Coordinates getCoordsToVillage(String village) throws APINoResultException {

		if (puffer.containsKey(village)) {
			return puffer.get(village);
		}

		Coordinates returnCoords = new Coordinates();

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();

		try {

			StringBuilder sb = new StringBuilder(BASE_URL);
			sb.append("?key=");
			sb.append(API_KEY);
			sb.append("&outFormat=json");
			sb.append("&location=");
			sb.append(village);
			sb.append("%2C+Germany"); // Für bessere Ergebnisse: Beschränkung auf Deutschland
			sb.append("&maxResults=1");

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}

		} catch (MalformedURLException e) {
			System.err.println("Error processing Places API URL" + e);
			throw new APINoResultException();
		} catch (IOException e) {
			System.err.println("Error connecting to Places API" + e);
			throw new APINoResultException();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		try {
			JSONObject jsonObj = new JSONObject(jsonResults.toString());

			JSONArray results = jsonObj.getJSONArray("results");
			JSONArray locations = results.getJSONObject(0).getJSONArray("locations");
			JSONObject coordinates = locations.getJSONObject(0).getJSONObject("displayLatLng");
			returnCoords.setLatitude(coordinates.getDouble("lat"));
			returnCoords.setLongitude(coordinates.getDouble("lng"));
		} catch (JSONException e) {
			System.err.println("Unable to Parse JSON to Coords");
			throw new APINoResultException();
		}
		puffer.put(village, returnCoords);
		return returnCoords;
	}
}
