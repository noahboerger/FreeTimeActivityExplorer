package org.dhbw.mosbach.ai.freetimeactivityexplorer.model;

public class Activity {

	private double latitude;
	private double longitude;
	private String activityLabel;

	public Activity(double latitude, double longitude, String activityLabel) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.activityLabel = activityLabel;
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
	
	public String getActivityLabel() {
		return activityLabel;
	}

	public void setActivityLabel(String activityLabel) {
		this.activityLabel = activityLabel;
	}
}
