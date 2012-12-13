package org.diningdevelopers.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "votes")
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private Location location;

	@ManyToOne(optional = false)
	private Developer developer;

	@Column(nullable = false)
	private Integer vote;

	@Column(name="vote_date", nullable = false)
	private Date date;

	public Date getDate() {
		return date;
	}

	public Developer getDeveloper() {
		return developer;
	}

	public Long getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	public Integer getVote() {
		return vote;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setVote(Integer vote) {
		this.vote = vote;
	}


}
