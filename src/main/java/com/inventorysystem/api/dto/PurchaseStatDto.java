package com.inventorysystem.api.dto;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PurchaseStatDto {
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
