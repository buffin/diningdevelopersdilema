package org.diningdevelopers.core.database.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="audit")
public class AuditEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="auditdate", nullable=false)
	private Date date;

	@Column(name="username", nullable=false)
	private String username;

	@Column(name="message", nullable=false)
	private String message;

	public Date getDate() {
		return date;
	}

	public Long getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public String getUsername() {
		return username;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
