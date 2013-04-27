package org.diningdevelopers.business.boundary;

public interface EventBoundary {

	void reopenVoting();

	void closeVoting();

	void openVoting();

	String getLatestEventState();

	boolean isLatestEventClosed();

	boolean isVotingOpen();

}
