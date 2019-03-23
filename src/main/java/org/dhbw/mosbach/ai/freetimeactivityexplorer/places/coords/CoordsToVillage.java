package org.dhbw.mosbach.ai.freetimeactivityexplorer.places.coords;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class CoordsToVillage {
	
	private final static String BASE_URL = "http://www.mapquestapi.com/geocoding/v1/address";
	
	//15 000 Anfragen frei
	private final static String API_KEY = "F5JOdf5ICpDl13pWwomhONGeJJdfHRtZ";

	public static Coordinates getCoordsToVillage(String village) {
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
			sb.append("%2C+Germany");
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
			return null;
		} catch (IOException e) {
			System.err.println("Error connecting to Places API" + e);
			return null;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

        JSONObject jsonObj = new JSONObject(jsonResults.toString());
        
        JSONArray results = jsonObj.getJSONArray("results");
        JSONArray locations = results.getJSONObject(0).getJSONArray("locations");
        JSONObject coordinates = locations.getJSONObject(0).getJSONObject("displayLatLng");
        returnCoords.setLatitude(coordinates.getDouble("lat"));
        returnCoords.setLongitude(coordinates.getDouble("lng"));

		return returnCoords;
	}
}
