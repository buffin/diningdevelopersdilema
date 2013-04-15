package org.diningdevelopers.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.diningdevelopers.core.business.persistence.AuditPersistence;
import org.diningdevelopers.model.AuditModel;

@Named
@RequestScoped
public class ViewAuditController implements Serializable {

	@Inject
	private AuditPersistence auditPersistence;

	public List<AuditModel> getLatest() {
		return auditPersistence.findLatest(100);
	}
}
