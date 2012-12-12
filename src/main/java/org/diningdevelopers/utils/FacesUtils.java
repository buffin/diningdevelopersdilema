package org.diningdevelopers.utils;

import javax.faces.context.FacesContext;

public class FacesUtils {

	public static FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}

	public static boolean isNotPostback() {
		return isPostback() == false;
	}

	public static boolean isPostback() {
		return getContext().isPostback();
	}
}
