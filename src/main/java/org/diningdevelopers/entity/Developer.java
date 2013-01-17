package org.diningdevelopers.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "developers")
public class Developer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "passowrd", nullable = false)
	private String password;

	@Column(name = "email", nullable = true)
	private String email;

	@Column(name="participating", nullable = true)
	private Boolean participating;

	public String getEmail() {
		return email;
	}

	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}

	public Boolean getParticipating() {
		return participating;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParticipating(Boolean participating) {
		this.participating = participating;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}
