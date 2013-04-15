package org.diningdevelopers.core.business.helper;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

@Named
public class TransactionHelper {

	@PersistenceContext
	private EntityManager entityManager;

	public void lockWritePessimistic(Object o) {
		entityManager.lock(o, LockModeType.PESSIMISTIC_WRITE);
	}

	public void lockReadPessimistic(Object o) {
		entityManager.lock(o, LockModeType.PESSIMISTIC_READ);
	}

	public void releaseLock(Object o) {
		entityManager.lock(o, LockModeType.NONE);
	}

	public void flush() {
		entityManager.flush();
	}
}
