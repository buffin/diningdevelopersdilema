package org.diningdevelopers.core.business.model;

import java.util.Date;


public class Event {
	
	private Long id;

	private Date date;

	private VotingState state = VotingState.Open;

	private Integer result;
	
	private Location winningLocation;

	public Event() {
	}
	
	public Event(Date date, VotingState state) {
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

	public Location getWinningLocation() {
		return winningLocation;
	}

	public void setWinningLocation(Location winningLocation) {
		this.winningLocation = winningLocation;
	}
}
