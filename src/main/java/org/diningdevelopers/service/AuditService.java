package org.diningdevelopers.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.dao.AuditDao;
import org.diningdevelopers.entity.Audit;

@Stateless
public class AuditService {

	@Inject
	private AuditDao auditDao;

	public void createAudit(String username, String message) {
		Audit audit = new Audit();
		audit.setDate(new Date());
		audit.setMessage(message);
		audit.setUsername(username);
		auditDao.save(audit);
	}


	public List<Audit> findLatest(int maxResult) {
		return auditDao.findLatest(maxResult);
	}

}
