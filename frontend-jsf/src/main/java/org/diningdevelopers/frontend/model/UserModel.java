package org.diningdevelopers.frontend.model;



public class UserModel {

	private Long id;
	private String username;
	private String name;
	private String email;
	private Boolean participating;
	private String password;

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

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
