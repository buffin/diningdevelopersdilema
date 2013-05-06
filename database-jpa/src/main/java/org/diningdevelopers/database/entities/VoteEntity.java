package org.diningdevelopers.database.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "votes")
public class VoteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name="location_id")
	private LocationEntity location;

	@ManyToOne(optional=false)
	@JoinColumn(name="event_id")
	private EventEntity event;
	
	@Column(name = "current", nullable = false)
	private Boolean current;

	@ManyToOne(optional = false)
	@JoinColumn(name="developer_id")
	private UserEntity user;

	@Column(nullable = false)
	private Integer vote;

	@Column(name = "vote_date", nullable = false)
	private Date date;

	public Date getDate() {
		return date;
	}
	public UserEntity getUser() {
		return user;
	}

	public EventEntity getEvent() {
		return event;
	}

	public Long getId() {
		return id;
	}

	public LocationEntity getLocation() {
		return location;
	}
	
	public Boolean getCurrent() {
		return current;
	}
	public void setCurrent(Boolean current) {
		this.current = current;
	}
	public Integer getVote() {
		return vote;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public void setEvent(EventEntity event) {
		this.event = event;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLocation(LocationEntity location) {
		this.location = location;
	}

	public void setVote(Integer vote) {
		this.vote = vote;
	}

}
