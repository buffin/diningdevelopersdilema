package org.diningdevelopers.core.business.boundary;

import java.util.List;

import org.diningdevelopers.core.business.model.Audit;

public interface AuditBoundary {

	List<Audit> findLatest();

}
