package org.diningdevelopers.business.boundary;

import org.diningdevelopers.business.model.Event;

public interface EventBoundary {

	void reopenVoting();

	void closeVoting();

	void openVoting();

	String getLatestEventState();

	boolean isLatestEventClosed();

	boolean isVotingOpen();
	
	Event getLatestEvent();

}
