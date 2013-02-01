package org.diningdevelopers.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.diningdevelopers.service.DeveloperService;
import org.diningdevelopers.utils.Authentication;
import org.diningdevelopers.utils.FacesUtils;

@Named
@RequestScoped
public class SettingsController {

	@Inject
	private DeveloperService developerService;

	private String password;
	private String passwordRepeated;

	private String email;

	public void init(ComponentSystemEvent event) {
		if (FacesUtils.isNotPostback()) {
			email = developerService.getMailAddress(Authentication.getUsername());
		}
	}

	public String updateMailAddress() {
		try {
			developerService.updateMailAddress(Authentication.getUsername(), email);
			FacesUtils.addMessage("Die E-Mail Adresse wurde geändert.");
		} catch (Exception e) {
			FacesUtils.addMessage("Fehler beim Ändern der E-Mail Adresse");
		}

		email = developerService.getMailAddress(Authentication.getUsername());

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
			developerService.changePassword(Authentication.getUsername(), password);
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
