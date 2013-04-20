package org.diningdevelopers.core.frontend.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.diningdevelopers.core.business.boundary.AuditBoundary;
import org.diningdevelopers.core.business.model.Audit;
import org.diningdevelopers.core.frontend.model.AuditModel;
import org.diningdevelopers.core.util.MappingService;

@Named
@RequestScoped
public class ViewAuditController implements Serializable {

	@Inject
	private AuditBoundary auditBoundary;
	
	@Inject
	private MappingService mappingService;

	public List<AuditModel> getLatest() {
		List<Audit> audits = auditBoundary.findLatest();
		return mappingService.mapCollection(audits, AuditModel.class);
	}
}
