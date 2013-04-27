package org.diningdevelopers.database.gateways;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.business.model.Audit;
import org.diningdevelopers.business.persistence.AuditPersistence;
import org.diningdevelopers.database.dao.AuditDao;
import org.diningdevelopers.database.dao.EventDao;
import org.diningdevelopers.database.entities.AuditEntity;
import org.diningdevelopers.util.MappingService;

@Stateless
public class AuditGateway implements AuditPersistence {

	@Inject
	private AuditDao auditDao;

	@Inject
	private EventDao eventDao;

	@Inject
	private MappingService mappingService;

	@Override
	public void createAudit(String username, String message) {
		AuditEntity audit = new AuditEntity();
		audit.setDate(new Date());
		audit.setMessage(message);
		audit.setUsername(username);
		auditDao.save(audit);
	}


	@Override
	public List<Audit> findLatest(int maxResult) {
		Date filterDate = eventDao.findLatestActiveVoting();
		List<AuditEntity> audits = auditDao.findLatest(maxResult, filterDate);

		return mappingService.mapCollection(audits, Audit.class);
	}

}
