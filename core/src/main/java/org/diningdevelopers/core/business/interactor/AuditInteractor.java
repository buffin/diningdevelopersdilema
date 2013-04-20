package org.diningdevelopers.core.business.interactor;

import java.util.List;

import javax.inject.Inject;

import org.diningdevelopers.core.business.boundary.AuditBoundary;
import org.diningdevelopers.core.business.model.Audit;
import org.diningdevelopers.core.business.persistence.AuditPersistence;

public class AuditInteractor implements AuditBoundary {

	@Inject
	private AuditPersistence auditPersistence;
	
	@Override
	public List<Audit> findLatest() {
		return auditPersistence.findLatest(100);
	}

}
