package org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.search;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.APINoResultException;
import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.Coordinates;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;


public class GooglePlacesAPI {
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_SEARCH = "/search";
    private static final String OUT_JSON = "/json";
    
    private static final int SEARCH_RADIUS = 10000;

    private static final String API_KEY = "AIzaSyA0eRPuS8wbevlN8bGeGZSZYAeRiZdVeE0";
    private static final String API_KEY_BOERGER = "AIzaSyBS4pL3epWzHIg_K4v1sNGL0HYajVnNMcc";

    private static HashMap<Coordinates, HashMap<String, Long>> lastUpdated = new HashMap<>();
    private static HashMap<Coordinates, HashMap<String, ArrayList<Place>>> puffer = new HashMap<>();
    
    public static ArrayList<Place> search(String keyword, Coordinates coordinates) throws APINoResultException {
        
    	if(puffer.containsKey(coordinates)) {
    		if(puffer.get(coordinates).containsKey(keyword)) {
    			//update every 60 s
    			if(System.currentTimeMillis() - lastUpdated.get(coordinates).get(keyword) < 60000) {
    				return puffer.get(coordinates).get(keyword);
    			}
    		}
    	}
    	
    	ArrayList<Place> resultList = new ArrayList<Place>();

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);
            sb.append(TYPE_SEARCH);
            sb.append(OUT_JSON);
            sb.append("?sensor=false");
            sb.append("&key=" + API_KEY_BOERGER);
            sb.append("&keyword=" + URLEncoder.encode(keyword, "utf8"));
            sb.append("&location=" + String.valueOf(coordinates.getLatitude()) + "," + String.valueOf(coordinates.getLongitude()));
            sb.append("&radius=" + String.valueOf(SEARCH_RADIUS));

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
            System.out.println(jsonObj);
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");

            resultList = new ArrayList<Place>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                Place place = new Place();
                place.setActivityType(keyword);
                place.setReference(predsJsonArray.getJSONObject(i).getString("reference"));
                place.setName(predsJsonArray.getJSONObject(i).getString("name"));
                place.setAddress(predsJsonArray.getJSONObject(i).getString("vicinity"));
                place.setRating(predsJsonArray.getJSONObject(i).getDouble("rating"));
                JSONObject geoJson = predsJsonArray.getJSONObject(i).getJSONObject("geometry");
                JSONObject locationJson = geoJson.getJSONObject("location");
                place.getCoordinates().setLatitude(locationJson.getDouble("lat"));
                place.getCoordinates().setLongitude(locationJson.getDouble("lng"));
                resultList.add(place);
            }
        } catch (JSONException e) {
            System.err.println( "Error processing JSON results"+ e);
            throw new APINoResultException();
        }

        if(!lastUpdated.containsKey(coordinates)) {
        	HashMap<String, Long> updated = new HashMap<>();
        	updated.put(keyword, System.currentTimeMillis());
        	lastUpdated.put(coordinates, updated);
        	
        	HashMap<String, ArrayList<Place>> puffered = new HashMap<>();
        	puffered.put(keyword, resultList);
        	puffer.put(coordinates, puffered);
        } else {
        	lastUpdated.get(coordinates).put(keyword, System.currentTimeMillis());
        	puffer.get(coordinates).put(keyword, resultList);
        }
        return resultList;
    }
}
