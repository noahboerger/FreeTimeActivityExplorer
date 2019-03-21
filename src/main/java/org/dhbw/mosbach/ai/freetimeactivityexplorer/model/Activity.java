package org.dhbw.mosbach.ai.freetimeactivityexplorer.model;

public class Activity {
	
	private double locationX;
	private double locationY;
	private String activityLabel;
	
	public Activity(double locationX, double locationY,String activityLabel) {
		this.locationX = locationX;
		this.locationY = locationY;
		this.activityLabel = activityLabel;
	}

	public double getLocationX() {
		return locationX;
	}

	public void setLocationX(double locationX) {
		this.locationX = locationX;
	}

	public double getLocationY() {
		return locationY;
	}

	public void setLocationY(double locationY) {
		this.locationY = locationY;
	}

	public String getActivityLabel() {
		return activityLabel;
	}

	public void setActivityLabel(String activityLabel) {
		this.activityLabel = activityLabel;
	}
		
}
