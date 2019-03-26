package org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.search;

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


public class GooglePlacesAPI {
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";

    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String TYPE_SEARCH = "/search";

    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyA0eRPuS8wbevlN8bGeGZSZYAeRiZdVeE0";

    private static final String API_KEY_BOERGER = "AIzaSyBS4pL3epWzHIg_K4v1sNGL0HYajVnNMcc";
    
    public static ArrayList<Place> autocomplete(String input) {
        ArrayList<Place> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);
            sb.append(TYPE_AUTOCOMPLETE);
            sb.append(OUT_JSON);
            sb.append("?sensor=false");
            sb.append("&key=" + API_KEY);
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

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
            return resultList;
        } catch (IOException e) {
            System.err.println("Error connecting to Places API" + e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            resultList = new ArrayList<Place>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                Place place = new Place();
                place.setReference(predsJsonArray.getJSONObject(i).getString("reference"));
                place.setName(predsJsonArray.getJSONObject(i).getString("description"));
                resultList.add(place);
            }
        } catch (JSONException e) {
            System.err.println("Error processing JSON results" + e);
        }

        return resultList;
    }

    public static ArrayList<Place> search(String keyword, double latitude, double longitude, int radius) {
        ArrayList<Place> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE);
            sb.append(TYPE_SEARCH);
            sb.append(OUT_JSON);
            sb.append("?sensor=false");
            sb.append("&key=" + API_KEY);
            sb.append("&keyword=" + URLEncoder.encode(keyword, "utf8"));
            sb.append("&location=" + String.valueOf(latitude) + "," + String.valueOf(longitude));
            sb.append("&radius=" + String.valueOf(radius));

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
            return resultList;
        } catch (IOException e) {
            System.err.println("Error connecting to Places API" + e);
            return resultList;
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
        }

        return resultList;
    }
}
