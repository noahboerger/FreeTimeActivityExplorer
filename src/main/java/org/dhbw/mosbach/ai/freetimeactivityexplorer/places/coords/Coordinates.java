package org.dhbw.mosbach.ai.freetimeactivityexplorer.places.coords;

public class Coordinates {
	double latitude;
	double longitude;

	public Coordinates() {
		longitude = 0;
		latitude = 0;
	}
	
	public Coordinates(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
