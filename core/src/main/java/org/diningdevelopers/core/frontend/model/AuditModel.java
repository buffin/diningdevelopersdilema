package org.diningdevelopers.core.frontend.model;

import java.util.Date;

public class AuditModel {

	private Date date;
	private String username;
	private String message;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
