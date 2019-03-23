package org.dhbw.mosbach.ai.freetimeactivityexplorer.places.search;

import java.util.List;

import org.dhbw.mosbach.ai.freetimeactivityexplorer.model.SearchLabel;

public class PlacesSearch {
	
	private final String apiKey = "AIzaSyA0eRPuS8wbevlN8bGeGZSZYAeRiZdVeE0";
	
	private String village;
	
	List<SearchLabel> searchLabelList;
	
	public PlacesSearch() {
		
	}
	

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public List<SearchLabel> getSearchLabelList() {
		return searchLabelList;
	}

	public void setSearchLabelList(List<SearchLabel> searchLabelList) {
		this.searchLabelList = searchLabelList;
	}
}
