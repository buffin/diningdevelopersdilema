package org.diningdevelopers.core.database.dao.helper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;


public class CriteriaHelper<T> {

	private final EntityManager entityManager;
	private final Class<T> persistentClass;
	private final CriteriaBuilder builder;
	private final CriteriaQuery<T> query;
	private final Root<T> root;
	private final List<Predicate> list = new ArrayList<Predicate>();
	private final List<Order> order = new ArrayList<Order>();
	private Integer maxResults = null;
	private boolean cacheable = false;

	public CriteriaHelper(EntityManager entityManager, Class<T> persistentClass) {
		this.entityManager = entityManager;
		this.persistentClass = persistentClass;
		this.builder = entityManager.getCriteriaBuilder();
		this.query = builder.createQuery(persistentClass);
		this.root = query.from(persistentClass);
	}

	public void add(Predicate p) {
		list.add(p);
	}

	public <E> void addEqual(Expression<E> expression, E value) {
		list.add(builder.equal(expression, value));
	}

	public <E> void addEqual(SingularAttribute<T, E> attribute, E value) {
		Expression<E> expression = root.get(attribute);
		addEqual(expression, value);
	}

	public <E extends Comparable<E>> void addGreaterThanOrEqualTo(Expression<E> expression, E value) {
		list.add(builder.greaterThanOrEqualTo(expression, value));
	}

	public <E extends Comparable<E>> void addGreaterThanOrEqualTo(SingularAttribute<T, E> attribute, E value) {
		Expression<E> expression = root.get(attribute);
		addGreaterThanOrEqualTo(expression, value);
	}

	public <E> void addIsNotNull(Path<E> path) {
		list.add(builder.isNotNull(path));
	}

	public <E> void addIsNotNull(SingularAttribute<T, E> attribute) {
		Path<E> expression = root.get(attribute);
		addIsNotNull(expression);
	}

	public void addIsNull(Path<?> path) {
		list.add(builder.isNull(path));
	}

	public <E> void addIsNull(SingularAttribute<T, E> attribute) {
		Path<E> expression = root.get(attribute);
		addIsNull(expression);
	}

	public <E extends Comparable<E>> void addLessThanOrEqualTo(Expression<E> expression, E value) {
		list.add(builder.lessThanOrEqualTo(expression, value));
	}

	public <E extends Comparable<E>> void addLessThanOrEqualTo(SingularAttribute<T, E> attribute, E value) {
		Expression<E> expression = root.get(attribute);
		addLessThanOrEqualTo(expression, value);
	}

	public void addLike(Expression<String> path, String string, MatchMode matchMode, boolean ignoreCase) {
		if (ignoreCase == true) {
			string = string.toLowerCase();
			path = builder.lower(path);
		}

		string = JpaUtils.prepareLike(string, matchMode);

		list.add(builder.like(path, string));
	}

	public void addLikeIgnoreCase(Expression<String> path, String string, MatchMode matchMode) {
		addLike(path, string, matchMode, true);
	}

	public void addOrder(Path<?> path, Boolean ascending) {
		if ((ascending == null) || (ascending == Boolean.TRUE)) {
			order.add(builder.asc(path));
		} else {
			order.add(builder.desc(path));
		}
	}

	public <E extends Comparable<E>> void addOrder(SingularAttribute<T, E> attribute, Boolean ascending) {
		Path<E> path = root.get(attribute);
		addOrder(path, ascending);
	}

	public void distinct(boolean b) {
		query.distinct(b);
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return builder;
	}

	public CriteriaQuery<T> getCriteriaQuery() {
		return query;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public List<T> getResultList() {
		TypedQuery<T> typedQuery = getTypedQuery();
		return typedQuery.getResultList();
	}

	public List<T> getResultList(Integer maxResults, boolean cacheable) {
		TypedQuery<T> typedQuery = getTypedQuery();

		if (maxResults != null) {
			typedQuery.setMaxResults(maxResults);
		}

		if (cacheable == true) {
			JpaUtils.setCacheable(typedQuery);
		}

		return typedQuery.getResultList();
	}

	public Root<T> getRoot() {
		return root;
	}

	public T getSingleResultOrNull() {
		TypedQuery<T> typedQuery = getTypedQuery();
		return JpaUtils.getSingleResultOrNull(typedQuery);
	}

	public TypedQuery<T> getTypedQuery() {
		Predicate[] where = JpaUtils.toArray(list);
		query.where(where);

		if (order.isEmpty() == false) {
			query.orderBy(order);
		}

		TypedQuery<T> typedQuery = entityManager.createQuery(query);

		if (cacheable == true) {
			JpaUtils.setCacheable(typedQuery);
		}

		if (maxResults != null) {
			typedQuery.setMaxResults(maxResults);
		}

		return typedQuery;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
}
