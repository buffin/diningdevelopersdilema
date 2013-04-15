package org.diningdevelopers.core.database.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "votes", uniqueConstraints = @UniqueConstraint(columnNames = { "developer_id", "location_id" }))
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

	@ManyToOne(optional = false)
	@JoinColumn(name="developer_id")
	private UserEntity developer;

	@Column(nullable = false)
	private Integer vote;

	@Column(name = "vote_date", nullable = false)
	private Date date;

	public Date getDate() {
		return date;
	}
	public UserEntity getDeveloper() {
		return developer;
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

	public Integer getVote() {
		return vote;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDeveloper(UserEntity developer) {
		this.developer = developer;
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
