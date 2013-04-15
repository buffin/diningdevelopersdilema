package org.diningdevelopers.core.business.model;

import java.util.Date;


public class Event {

	private Date date;

	private VotingState state = VotingState.Open;

	private Integer result;

	public Event() {
	}

	public Event(Date date, VotingState state) {
		this.date = date;
		this.state = state;
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
}
