package org.diningdevelopers.model;

import java.util.ArrayList;
import java.util.List;

public class DecisionTable {

	private List<DecisionModel> decisions = new ArrayList<>();

	private List<DeveloperModel> developers = new ArrayList<>(); 


	public List<DecisionModel> getDecisions() {
		return decisions;
	}

	public List<DeveloperModel> getDevelopers() {
		return developers;
	}

	public void setDecisions(List<DecisionModel> decisions) {
		this.decisions = decisions;
	}

	public void setDevelopers(List<DeveloperModel> developers) {
		this.developers = developers;
	}
}

