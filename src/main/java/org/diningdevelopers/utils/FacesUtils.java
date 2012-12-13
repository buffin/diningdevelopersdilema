package org.diningdevelopers.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtils {

	public static void addMessage(String message) {
		FacesMessage facesMessage = new FacesMessage(message);
		getContext().addMessage(null, facesMessage);
	}

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
