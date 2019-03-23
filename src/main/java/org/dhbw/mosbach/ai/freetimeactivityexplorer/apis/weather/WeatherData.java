package org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.weather;

public class WeatherData {

	private double temperature;
	private boolean raining;

	public WeatherData() {
		temperature = 0;
		raining = false;

	}

	public WeatherData(double temperature, boolean raining) {
		this.temperature = temperature;
		this.raining = raining;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public boolean isRaining() {
		return raining;
	}

	public void setRaining(boolean raining) {
		this.raining = raining;
	}

	@Override
	public String toString() {
		return "WeatherData[temperature=" + temperature + " raining=" + raining;
	}
}
