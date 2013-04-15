package org.diningdevelopers.core.business.persistence;

import java.util.List;

import org.diningdevelopers.core.business.model.Audit;

public interface AuditPersistence {

	void createAudit(String username, String message);

	List<Audit> findLatest(int maxResult);

}
