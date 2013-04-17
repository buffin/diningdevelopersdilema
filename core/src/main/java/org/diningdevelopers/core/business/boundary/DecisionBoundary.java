package org.diningdevelopers.core.business.boundary;

import org.diningdevelopers.core.business.requestmodels.LatestVotesRequestModel;
import org.diningdevelopers.core.business.requestmodels.ResultRequestModel;

public interface DecisionBoundary {

	ResultRequestModel getResultModelForLatestVote();

	LatestVotesRequestModel findLatestVotesOfUsers();

}
