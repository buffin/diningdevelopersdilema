package org.diningdevelopers.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.primefaces.model.map.LatLng;


public class CoordinatesParserTest {
	
	private CoordinatesParser parser = new CoordinatesParser();;

	@Test
	public void canParseCommaSeparatedCoordinates() throws Exception {
		double lat = 45.123;
		double lng = 27.145;
		String coordinates = String.valueOf(lat) + "," + String.valueOf(lng);
		LatLng result = parser.parseCoordinates(coordinates);
		assertNotNull(result);
		assertEquals(lat, result.getLat(), 0.001);
		assertEquals(lng, result.getLng(), 0.001);
	}
	
	@Test
	public void canParseCommaSeparatedCoordinatesWithSpaces() throws Exception {
		double lat = 45.123;
		double lng = 27.145;
		String coordinates = String.valueOf(lat) + " , " + String.valueOf(lng);
		LatLng result = parser.parseCoordinates(coordinates);
		assertNotNull(result);
		assertEquals(lat, result.getLat(), 0.001);
		assertEquals(lng, result.getLng(), 0.001);
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
		parser.parseCoordinates("41.254 45.658");
	}

}
