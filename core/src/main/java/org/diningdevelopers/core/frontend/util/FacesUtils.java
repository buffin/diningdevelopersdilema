package org.diningdevelopers.core.frontend.util;

import javax.faces.context.FacesContext;


public class FacesUtils {

	private static FacesHelper helper = new FacesHelper();

	public static void addMessage(String message) {
		helper.addMessage(message);
	}

	public static FacesContext getContext() {
		return helper.getContext();
	}

	public static boolean isNotPostback() {
		return helper.isNotPostback();
	}

	public static boolean isPostback() {
		return helper.isPostback();
	}

}
