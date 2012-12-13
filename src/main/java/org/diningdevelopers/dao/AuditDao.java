package org.diningdevelopers.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.diningdevelopers.entity.Audit;

@Named
public class AuditDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Audit> findLatest(int maxResult) {
		CriteriaHelper<Audit> helper = new CriteriaHelper<>(entityManager, Audit.class);

		if (maxResult > 0) {
			helper.setMaxResults(maxResult);
		}

		return helper.getResultList();
	}

	public void save(Audit audit) {
		entityManager.persist(audit);
	}
}
