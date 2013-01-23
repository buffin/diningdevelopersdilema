package org.diningdevelopers.timer;

import java.util.Calendar;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.dao.VotingDao;
import org.diningdevelopers.entity.Voting;

@Stateless
public class VotingTimer {
	
	@Inject
	private VotingDao votingDao;

	@Schedule(dayOfWeek="1-5", hour="11", minute="15", persistent=false)
	public void closeVoting() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Voting voting = votingDao.findVotingForDate(cal.getTime());
		if (voting == null) {
			Voting closedVoting = new Voting(Calendar.getInstance().getTime(), true);
			votingDao.save(closedVoting);
		} else {
			voting.setClosed(true);
			votingDao.save(voting);
		}
	}
	
	@Schedule(dayOfWeek="1-5", hour="6", minute="0", persistent=false)
	public void openVoting() {
		Voting voting = new Voting(Calendar.getInstance().getTime(), false);
		votingDao.save(voting);
		
		votingDao.removeAllVotes();
	}
	
}
