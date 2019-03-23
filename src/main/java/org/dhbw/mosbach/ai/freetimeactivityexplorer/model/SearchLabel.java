package org.dhbw.mosbach.ai.freetimeactivityexplorer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "searchLabels")
@XmlRootElement
public class SearchLabel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlTransient
	private int id;

	private String searchLabel;

	private double temperatureMin;
	private double temperatueMax;

	private boolean rainAllowed;

	public SearchLabel() {
		super();
	}

	public SearchLabel(String searchLabel, double temperatureMin, double temperatueMax, boolean rainAllowed) {
		super();
		this.searchLabel = searchLabel;
		this.temperatureMin = temperatureMin;
		this.temperatueMax = temperatueMax;
		this.rainAllowed = rainAllowed;
	}

	public String getSearchLabel() {
		return searchLabel;
	}

	public void setSearchLabel(String searchLabel) {
		this.searchLabel = searchLabel;
	}

	public double getTemperatureMin() {
		return temperatureMin;
	}

	public void setTemperatureMin(double temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

	public double getTemperatueMax() {
		return temperatueMax;
	}

	public void setTemperatueMax(double temperatueMax) {
		this.temperatueMax = temperatueMax;
	}

	public boolean isRainAllowed() {
		return rainAllowed;
	}

	public void setRainAllowed(boolean rainAllowed) {
		this.rainAllowed = rainAllowed;
	}

	@Override
	public String toString() {
		return "SearchLabel[id=" + id + " searchLabel=" + searchLabel + " tempMin=" + temperatureMin + " tempMax="
				+ temperatueMax + "rainAllowed=" + rainAllowed + "]";
	}

}
