package org.diningdevelopers.database.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.diningdevelopers.business.model.VotingState;


@Entity
@Table(name = "events")
public class EventEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "votingdate", nullable = false)
	private Date date;

	@Column(name = "state", nullable = false)
	@Enumerated(EnumType.STRING)
	private VotingState state = VotingState.Open;

	@Column(name = "result")
	private Integer result;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="winning_location_id")
	private LocationEntity winningLocation;

	public EventEntity() {
	}

	public EventEntity(Date date, VotingState state) {
		this.date = date;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public VotingState getState() {
		return state;
	}

	public void setState(VotingState state) {
		this.state = state;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public LocationEntity getWinningLocation() {
		return winningLocation;
	}

	public void setWinningLocation(LocationEntity winningLocation) {
		this.winningLocation = winningLocation;
	}
}
