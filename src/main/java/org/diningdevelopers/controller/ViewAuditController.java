package org.diningdevelopers.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.diningdevelopers.entity.Audit;
import org.diningdevelopers.service.AuditService;

@Named
@RequestScoped
public class ViewAuditController implements Serializable {

	@Inject
	private AuditService auditService;

	public List<Audit> getLatest() {
		return auditService.findLatest(100);
	}
}
