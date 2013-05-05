package org.diningdevelopers.frontend.helper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Conversation;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ApplicationScoped
public class FacesHelper {

	public void addMessage(String message) {
		FacesMessage facesMessage = new FacesMessage(message);
		getContext().addMessage(null, facesMessage);
	}

	public FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}

	public boolean isNotPostback() {
		return isPostback() == false;
	}

	public boolean isPostback() {
		return getContext().isPostback();
	}

	public void beginConversation(Conversation conversation) {
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

	public void endConversation(Conversation conversation) {
		if (conversation.isTransient() == false) {
			conversation.end();
		}
	}
}
