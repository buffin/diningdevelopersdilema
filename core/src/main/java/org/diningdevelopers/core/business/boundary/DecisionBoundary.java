package org.diningdevelopers.core.business.boundary;

import org.diningdevelopers.core.business.responsemodels.LatestVotesResponseModel;
import org.diningdevelopers.core.business.responsemodels.ResultReponseModel;

public interface DecisionBoundary {

	ResultReponseModel getResultModelForLatestVote();

	LatestVotesResponseModel findLatestVotesOfUsers();

}
