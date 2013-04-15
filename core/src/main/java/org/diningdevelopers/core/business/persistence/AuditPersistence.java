package org.diningdevelopers.core.business.persistence;

import java.util.List;

import org.diningdevelopers.model.AuditModel;

public interface AuditPersistence {

	void createAudit(String username, String message);

	List<AuditModel> findLatest(int maxResult);

}
