package org.diningdevelopers.core.business;

import java.util.List;

import javax.inject.Inject;

import org.diningdevelopers.core.business.model.Audit;
import org.diningdevelopers.core.business.persistence.AuditPersistence;

public class AuditInteractor {

	@Inject
	private AuditPersistence auditPersistence;
	
	public List<Audit> findLatest() {
		return auditPersistence.findLatest(100);
	}

}
