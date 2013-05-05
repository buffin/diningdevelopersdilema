package org.diningdevelopers.business.boundary;

import java.util.List;

import org.diningdevelopers.business.model.Audit;

public interface AuditBoundary {

	List<Audit> findLatest();

}
