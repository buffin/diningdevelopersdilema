package org.diningdevelopers.core.database.dao.helper;

import java.util.Collection;
import java.util.List;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;


public class JpaUtils {

	public static void evictCache(EntityManager entityManager) {
		Cache cache = entityManager.getEntityManagerFactory().getCache();
		cache.evictAll();
	}

	public static <T> T getSingleResultOrNull(TypedQuery<T> typedQuery) {
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public static <T> List<T> getResultList(EntityManager entityManager, CriteriaQuery<T> criteria) {
		TypedQuery<T> typedQuery = entityManager.createQuery(criteria);
		return typedQuery.getResultList();
	}

	@Deprecated
	public static Predicate like(CriteriaBuilder builder, Expression<String> nameExpression, String string, MatchMode matchMode) {
		string = prepareLike(string, matchMode);
		return builder.like(nameExpression, string);
	}

	public static String prepareLike(String string, MatchMode matchMode) {
		StringBuilder sb = new StringBuilder();

		if ((matchMode == MatchMode.End) || (matchMode == MatchMode.Anywhere)) {
			sb.append("%");
		}

		sb.append(string);

		if ((matchMode == MatchMode.Start) || (matchMode == MatchMode.Anywhere)) {
			sb.append("%");
		}

		return sb.toString();
	}

	public static <T> T remove(EntityManager entityManager, T object) {
		if (entityManager.contains(object) == false) {
			object = entityManager.merge(object);
		}
		entityManager.remove(object);

		return object;
	}

	public static <T> void persistAll(EntityManager entityManager, Collection<T> collection) {
		for (T t : collection) {
			entityManager.persist(t);
		}
	}

	public static <T> List<T> findAll(EntityManager entityManager, Class<T> type) {
		CriteriaHelper<T> criteriaHelper = new CriteriaHelper<T>(entityManager, type);
		return criteriaHelper.getResultList();
	}

	public static <T> void setCacheable(TypedQuery<T> query) {
		query.setHint("org.hibernate.cacheable", true);
		// TODO EclipseLink oder generisch?
	}

	public static Predicate[] toArray(List<Predicate> list) {
		return list.toArray(new Predicate[list.size()]);
	}
}
