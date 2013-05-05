package org.diningdevelopers.database.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.diningdevelopers.database.dao.helper.CriteriaHelper;
import org.diningdevelopers.database.entities.AuditEntity;
import org.diningdevelopers.database.entities.AuditEntity_;

@Stateless
public class AuditDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<AuditEntity> findLatest(int maxResult, Date filterDate) {
		CriteriaHelper<AuditEntity> helper = new CriteriaHelper<>(entityManager, AuditEntity.class);

		if (maxResult > 0) {
			helper.setMaxResults(maxResult);
		}

		helper.addOrder(AuditEntity_.date, false);
		helper.addLessThanOrEqualTo(AuditEntity_.date, filterDate);

		return helper.getResultList();
	}

	public void save(AuditEntity audit) {
		entityManager.merge(audit);
	}
}

