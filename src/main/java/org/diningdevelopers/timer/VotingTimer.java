package org.diningdevelopers.timer;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.service.DecisionService;
import org.diningdevelopers.service.VoteService;

@Stateless
public class VotingTimer {

	@Inject
	private VoteService voteService;

	@Inject
	private DecisionService decisionService;

	@Schedule(dayOfWeek="1-5", hour="11", minute="15", persistent=false)
	public void closeVoting() {
		voteService.closeVoting();
		decisionService.determineResultForVoting();
	}

	@Schedule(dayOfWeek="1-5", hour="6", minute="0", persistent=false)
	public void openVoting() {
		voteService.openVoting();
	}

}
