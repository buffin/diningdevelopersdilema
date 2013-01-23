package org.diningdevelopers.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
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
	

	
	

}
