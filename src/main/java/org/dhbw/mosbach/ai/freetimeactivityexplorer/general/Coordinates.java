package org.dhbw.mosbach.ai.freetimeactivityexplorer.general;

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

	@Override
	public String toString() {
		return "Coordinates[lat=" + latitude + " long=" + longitude + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Coordinates) {
			Coordinates compare = (Coordinates) obj;
			if (compare.latitude == latitude && compare.longitude == longitude) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) (latitude * 100 + longitude * 1000000);
	}
}
