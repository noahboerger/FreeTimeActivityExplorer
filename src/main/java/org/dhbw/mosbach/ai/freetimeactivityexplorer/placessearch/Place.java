package org.dhbw.mosbach.ai.freetimeactivityexplorer.placessearch;

public class Place {

	private String reference;
	private String name;
	private String icon;
	private String formatted_address;
	private String formatted_phone_number;

	public Place() {
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public String getFormatted_phone_number() {
		return formatted_phone_number;
	}

	public void setFormatted_phone_number(String formatted_phone_number) {
		this.formatted_phone_number = formatted_phone_number;
	}

	@Override
	public String toString() {
		return "Place[reference=" + reference + " name=" + name + " icon=" + icon + " formatted_address="
				+ formatted_address + " formatted_phone_number=" + formatted_phone_number + "]";
	}
}
