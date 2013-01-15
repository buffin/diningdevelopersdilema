package org.diningdevelopers.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.diningdevelopers.dao.DeveloperDao;
import org.diningdevelopers.dao.VotingDao;
import org.diningdevelopers.model.DecisionModel;

@Stateless
public class DecisionService {

	private DeveloperDao developerDao;

	private VotingDao votingDao;

	public List<DecisionModel> buildDecisionModel(Date date) {
		return null;
	}
}
