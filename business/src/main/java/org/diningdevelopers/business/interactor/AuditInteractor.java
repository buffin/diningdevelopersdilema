package org.diningdevelopers.business.interactor;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.diningdevelopers.business.boundary.AuditBoundary;
import org.diningdevelopers.business.model.Audit;
import org.diningdevelopers.business.persistence.AuditPersistence;

public class AuditInteractor implements AuditBoundary, Serializable {

	@Inject
	private AuditPersistence auditPersistence;
	
	@Override
	public List<Audit> findLatest() {
		return auditPersistence.findLatest(100);
	}

}
