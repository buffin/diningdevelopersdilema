package org.diningdevelopers.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class InitialContextUtils {

	/**
	 * 
	 * @param name
	 * @return handles NamingException and returns null 
	 */
	public static <T> T doLookup(String name) {
		try {
			return InitialContext.doLookup(name);
		} catch (NamingException e) {
			return null;
		}
	}
}
