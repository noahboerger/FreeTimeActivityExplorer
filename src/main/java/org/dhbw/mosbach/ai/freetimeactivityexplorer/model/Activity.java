package org.dhbw.mosbach.ai.freetimeactivityexplorer.model;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.Coordinates;

public class Activity {

	private Coordinates coordinates;
	
	private String activityLabel;
	private String address;

	public Activity(Coordinates coordinates, String activityLabel, String address) {
		this.coordinates = coordinates;
		this.activityLabel = activityLabel;
		this.address = address;
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
}
