package org.diningdevelopers.business.model;

import java.util.ArrayList;
import java.util.List;

public class SimpleMail {

	private List<String> to = new ArrayList<>();
	private String subject;
	private String body;
	private String contentType = "text/plain";

	public String getBody() {
		return body;
	}

	public String getContentType() {
		return contentType;
	}

	public String getSubject() {
		return subject;
	}

	public List<String> getTo() {
		return to;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public boolean addTo(String to) {
		return getTo().add(to);
	}
}
