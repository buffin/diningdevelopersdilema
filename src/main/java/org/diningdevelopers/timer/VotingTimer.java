package org.diningdevelopers.timer;

import java.util.Calendar;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.dao.VotingDao;
import org.diningdevelopers.entity.Voting;
import org.diningdevelopers.service.VoteService;

@Stateless
public class VotingTimer {
	
	@Inject
	private VoteService voteService;

	@Schedule(dayOfWeek="1-5", hour="11", minute="15", persistent=false)
	public void closeVoting() {
		voteService.closeVoting();
	}
	
	@Schedule(dayOfWeek="1-5", hour="6", minute="0", persistent=false)
	public void openVoting() {
		voteService.closeVoting();
	}
	
}
