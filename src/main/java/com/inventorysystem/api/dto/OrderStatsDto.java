package com.inventorysystem.api.dto;

import java.util.List;

 
public class OrderStatsDto {
	private List<String> labels;
	private List<Integer> data;
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public List<Integer> getData() {
		return data;
	}
	public void setData(List<Integer> data) {
		this.data = data;
	}

	
}
