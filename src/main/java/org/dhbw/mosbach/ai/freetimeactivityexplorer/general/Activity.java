package org.dhbw.mosbach.ai.freetimeactivityexplorer.general;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.Coordinates;

public class Activity {

	private Coordinates coordinates;

	private String activityLabel;
	private double rating;
	private String address;

	private String activityType;

	public Activity(Coordinates coordinates, String activityLabel, double rating, String address, String activityTye) {
		this.coordinates = coordinates;
		this.activityLabel = activityLabel;
		this.rating = rating;
		this.address = address;
		this.activityType = activityTye;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getActivityLabel() {
		return activityLabel;
	}

	public void setActivityLabel(String activityLabel) {
		this.activityLabel = activityLabel;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
}
