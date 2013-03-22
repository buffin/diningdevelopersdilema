package org.diningdevelopers.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.dao.AuditDao;
import org.diningdevelopers.dao.VotingDao;
import org.diningdevelopers.entity.Audit;
import org.diningdevelopers.model.AuditModel;

@Stateless
public class AuditService {

	@Inject
	private AuditDao auditDao;

	@Inject
	private VotingDao votingDao;

	@Inject
	private MappingService mappingService;

	public void createAudit(String username, String message) {
		Audit audit = new Audit();
		audit.setDate(new Date());
		audit.setMessage(message);
		audit.setUsername(username);
		auditDao.save(audit);
	}


	public List<AuditModel> findLatest(int maxResult) {
		Date filterDate = votingDao.findLatestActiveVoting();
		List<Audit> audits = auditDao.findLatest(maxResult, filterDate);

		return mappingService.mapCollection(audits, AuditModel.class);
	}

}
