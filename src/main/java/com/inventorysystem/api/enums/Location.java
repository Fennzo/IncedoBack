package com.inventorysystem.api.enums;


public enum Location {
	LONDON(51.5074, -0.1278),
	CARDIFF(51.4816, -3.1791),
	MANCHESTER(53.4830, -2.2441);

	private final double latitude;
	private final double longitude;

	Location(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
}
