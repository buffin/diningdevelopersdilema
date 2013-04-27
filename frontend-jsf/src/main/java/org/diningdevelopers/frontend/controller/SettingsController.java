package org.diningdevelopers.frontend.controller;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.diningdevelopers.business.boundary.UserBoundary;
import org.diningdevelopers.frontend.helper.Authentication;
import org.diningdevelopers.frontend.helper.FacesHelper;
import org.diningdevelopers.frontend.helper.FacesUtils;

@Named
@ConversationScoped
public class SettingsController implements Serializable {

	@Inject
	private UserBoundary userBoundary;

	@Inject
	private Conversation conversation;

	@Inject
	private FacesHelper facesHelper;

	private String password;
	private String passwordRepeated;

	private String email;

	public void init() {
		facesHelper.beginConversation(conversation);

		if (FacesUtils.isNotPostback()) {
			email = userBoundary.getMailAddress(Authentication.getUsername());
		}
	}

	public String updateMailAddress() {
		try {
			userBoundary.updateMailAddress(Authentication.getUsername(), email);
			FacesUtils.addMessage("Die E-Mail Adresse wurde geändert.");
		} catch (Exception e) {
			FacesUtils.addMessage("Fehler beim Ändern der E-Mail Adresse");
		}

		email = userBoundary.getMailAddress(Authentication.getUsername());

		return null;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeated() {
		return passwordRepeated;
	}

	public void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
	}

	public void changePassword() {
		if (StringUtils.equals(password, passwordRepeated)) {
			userBoundary.changePassword(Authentication.getUsername(), password);
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Die Passwörter stimmen nicht überein", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
