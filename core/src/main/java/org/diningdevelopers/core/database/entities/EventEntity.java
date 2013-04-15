package org.diningdevelopers.core.database.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


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
	private VotingStateEntity state = VotingStateEntity.Open;

	@Column(name = "result")
	private Integer result;

	public EventEntity() {
	}

	public EventEntity(Date date, VotingStateEntity state) {
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

	public VotingStateEntity getState() {
		return state;
	}

	public void setState(VotingStateEntity state) {
		this.state = state;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}
}
