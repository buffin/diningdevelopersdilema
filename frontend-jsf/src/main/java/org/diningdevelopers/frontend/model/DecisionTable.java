package org.diningdevelopers.frontend.model;

import java.util.ArrayList;
import java.util.List;


public class DecisionTable {

	private List<DecisionModel> decisions = new ArrayList<>();

	private List<UserModel> developers = new ArrayList<>(); 

	private float totalPoints = 0f;


	public List<DecisionModel> getDecisions() {
		return decisions;
	}

	public List<UserModel> getDevelopers() {
		return developers;
	}
	public float getTotalPoints() {
		return totalPoints;
	}

	public void setDecisions(List<DecisionModel> decisions) {
		this.decisions = decisions;
	}

	public void setDevelopers(List<UserModel> developers) {
		this.developers = developers;
	}

	public void setTotalPoints(float totalPoints) {
		this.totalPoints = totalPoints;
	}
}

