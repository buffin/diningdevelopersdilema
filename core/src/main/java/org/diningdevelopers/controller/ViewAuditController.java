package org.diningdevelopers.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.diningdevelopers.core.business.AuditService;
import org.diningdevelopers.core.business.MappingService;
import org.diningdevelopers.core.business.model.Audit;
import org.diningdevelopers.core.frontend.model.AuditModel;

@Named
@RequestScoped
public class ViewAuditController implements Serializable {

	@Inject
	private AuditService auditService;
	
	@Inject
	private MappingService mappingService;

	public List<AuditModel> getLatest() {
		List<Audit> audits = auditService.findLatest();
		return mappingService.mapCollection(audits, AuditModel.class);
	}
}
