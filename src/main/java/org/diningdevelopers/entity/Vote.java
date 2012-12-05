package org.diningdevelopers.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "votes", uniqueConstraints = @UniqueConstraint(columnNames = { "developer_id", "location_id" }))
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
