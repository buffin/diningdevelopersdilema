package org.diningdevelopers.controller;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.diningdevelopers.service.UserService;
import org.diningdevelopers.utils.Authentication;
import org.diningdevelopers.utils.FacesHelper;
import org.diningdevelopers.utils.FacesUtils;

@Named
@ConversationScoped
public class SettingsController implements Serializable {

	@Inject
	private UserService userService;

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
			email = userService.getMailAddress(Authentication.getUsername());
		}
	}

	public String updateMailAddress() {
		try {
			userService.updateMailAddress(Authentication.getUsername(), email);
			FacesUtils.addMessage("Die E-Mail Adresse wurde geändert.");
		} catch (Exception e) {
			FacesUtils.addMessage("Fehler beim Ändern der E-Mail Adresse");
		}

		email = userService.getMailAddress(Authentication.getUsername());

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
			userService.changePassword(Authentication.getUsername(), password);
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
