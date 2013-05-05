package org.diningdevelopers.business.boundary;

import org.diningdevelopers.business.model.Event;
import org.diningdevelopers.business.responsemodels.LatestVotesResponseModel;
import org.diningdevelopers.business.responsemodels.ResultReponseModel;

public interface DecisionBoundary {

	ResultReponseModel getResultModelForLatestVote();

	LatestVotesResponseModel findLatestVotesOfUsers();

	void determineResultForVoting(Event event);

}
