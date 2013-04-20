package org.diningdevelopers.core.frontend.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.diningdevelopers.core.frontend.util.CoordinatesParser;
import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.map.LatLng;


public class CoordinatesParserTest {
	
	CoordinatesParser parser;
	double lat;
	double lng;

	@Before
	public void setUp() {
		lat = 45.123;
		lng = 27.145;
		parser = new CoordinatesParser();;
	}
	
	@Test
	public void canParseCommaSeparatedCoordinates() throws Exception {
		String coordinates = createCoordinatesAsString(lat, lng);
		LatLng result = parser.parseCoordinates(coordinates);
		assertResultIsNotNullAndCoordinatesAreCorrect(result);
	}
	
	@Test
	public void canParseCommaSeparatedCoordinatesWithSpaces() throws Exception {
		String coordinates = createCoordinatesAsString(lat, lng);
		LatLng result = parser.parseCoordinates(coordinates);
		assertResultIsNotNullAndCoordinatesAreCorrect(result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void emptyStringLeadsToException() throws Exception {
		parser.parseCoordinates("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullArgumentLeadsToException() throws Exception {
		parser.parseCoordinates(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void missingDelimiterLeadsToException() throws Exception {
		String coordinates = "41.254 45.658";
		parser.parseCoordinates(coordinates);
	}

	private String createCoordinatesAsString(double lat, double lng) {
		return String.valueOf(lat) + " , " + String.valueOf(lng);
	}
	
	private void assertResultIsNotNullAndCoordinatesAreCorrect(LatLng result) {
		assertNotNull(result);
		assertEquals(lat, result.getLat(), 0.001);
		assertEquals(lng, result.getLng(), 0.001);
	}
}
