package org.dhbw.mosbach.ai.freetimeactivityexplorer.apis.places.search;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.general.Coordinates;

public class Place {

	private String reference;
	private String name;
	private String address;
	private String formatted_phone_number;

	private double rating;

	private Coordinates coords;

	public Place() {
		coords = new Coordinates();
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFormatted_phone_number() {
		return formatted_phone_number;
	}

	public void setFormatted_phone_number(String formatted_phone_number) {
		this.formatted_phone_number = formatted_phone_number;
	}

	public Coordinates getCoordinates() {
		return coords;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Place[reference=" + reference + " name=" + name + " location=" + coords + " address=" + address
				+ " formatted_phone_number=" + formatted_phone_number + "]";
	}
}
