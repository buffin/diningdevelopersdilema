package org.diningdevelopers.frontend.helper;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.map.LatLng;

public class CoordinatesParser implements Serializable {

	private static final String COORDINATES_PATTERN = "^\\d+.\\d+\\s*,\\s*\\d+.\\d+$";
	private static final String DELIMITER_SYMBOL = ",";

	public LatLng parseCoordinates(String coordinates) {
		if (StringUtils.isBlank(coordinates)) {
			throw new IllegalArgumentException("argument must not be empty");
		}
		if (isFormatCorrectOf(coordinates) == false) {
			throw new IllegalArgumentException("format of coordinates is wrong, example 42.175,23.142");
		}
		double lat = Double.valueOf(extractLatPart(coordinates).trim());
		double lng = Double.valueOf(extractLngPart(coordinates).trim());
		return new LatLng(lat, lng);
	}

	private boolean isFormatCorrectOf(String coordinates) {
		return coordinates.matches(COORDINATES_PATTERN);
	}

	private String extractLngPart(String coordinates) {
		return coordinates.substring(coordinates.indexOf(DELIMITER_SYMBOL) + 1);
	}

	private String extractLatPart(String coordinates) {
		return coordinates.substring(0, coordinates.indexOf(DELIMITER_SYMBOL));
	}
	
	
}
