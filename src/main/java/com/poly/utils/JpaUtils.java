package com.poly.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.poly.common.QueryParamemter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;

public class JpaUtils {

	public static final int NAME_QUERY = 0;
	public static final int JPQL_QUERY = 1;
	public static final int NATIVE_QUERY = 2;
	public static final String UNIT_NAME = "asm";

	@SuppressWarnings("unchecked")
	public static <T> List<T> excuteQuery(String queryValue, List<QueryParamemter> queryParamemters, int queryType) {
		List<T> list = new ArrayList<>();
		EntityManager em = getEntityManager();
		EntityTransaction tran = em.getTransaction();
		try {
			tran.begin();
			switch (queryType) {
			case NAME_QUERY: {
				TypedQuery<Object> query = em.createNamedQuery(queryValue, Object.class);
				if (queryParamemters != null) {
					setParamemter(query, queryParamemters);
				}
				list = (List<T>) query.getResultList();
				break;
			}
			case JPQL_QUERY: {
				TypedQuery<Object> query = em.createQuery(queryValue, Object.class);
				if (queryParamemters != null) {
					setParamemter(query, queryParamemters);
				}
				list = (List<T>) query.getResultList();
				break;
			}
			case NATIVE_QUERY: {
				Query query = em.createNamedQuery(queryValue, Object.class);
				if (queryParamemters != null) {
					setParamemter(query, queryParamemters);
				}
				list = query.getResultList();
				break;
			}
			default:
				throw new IllegalArgumentException("JpaUtils.excuteQuery: " + queryType + " not type of query!");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> findWithPagination(String nameQuery, List<QueryParamemter> queryParamemters, int offset,
			int limit) {
		List<T> list = new ArrayList<>();
		EntityManager em = getEntityManager();
		EntityTransaction tran = em.getTransaction();
		try {
			tran.begin();
			TypedQuery<Object> query = em.createNamedQuery(nameQuery, Object.class);
			if (queryParamemters != null) {
				setParamemter(query, queryParamemters);
			}
			query.setFirstResult(offset);
			query.setMaxResults(limit);
			list = (List<T>) query.getResultList();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			em.close();
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> findAll(String entityName) {
		List<T> list = new ArrayList<>();
		EntityManager em = getEntityManager();
		EntityTransaction tran = em.getTransaction();
		String jpql = "SELECT o FROM " + entityName + " o";
		try {
			tran.begin();
			Query query = em.createQuery(jpql, Object.class);
			list = (List<T>) query.getResultList();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> excuteStoredProcedure(String name, List<QueryParamemter> queryParamemters) {
		List<T> list = new ArrayList<>();
		EntityManager em = getEntityManager();
		EntityTransaction tran = em.getTransaction();
		try {
			tran.begin();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery(name);
			setParamemter(query, queryParamemters);
			list = query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return list;
	}

	public static int excuteUpdate(String queryValue, List<QueryParamemter> queryParamemters) {
		int rows = -1;
		EntityManager em = getEntityManager();
		EntityTransaction tran = em.getTransaction();
		try {
			tran.begin();
			Query query = em.createQuery(queryValue);
			setParamemter(query, queryParamemters);
			rows = query.executeUpdate();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return rows;
	}

	public static <T> T findById(Class<T> clazz, Object id) {
		EntityManager em = getEntityManager();
		EntityTransaction tran = em.getTransaction();
		try {
			tran.begin();
			return em.find(clazz, id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return null;
	}

	public static <T> T persist(Class<T> clazz, T entity) {
		EntityManager em = getEntityManager();
		EntityTransaction tran = em.getTransaction();
		try {
			tran.begin();
			em.persist(entity);
			tran.commit();
			return entity;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			tran.rollback();
		} finally {
			em.close();
		}
		return null;
	}

	public static <T> T merge(Class<T> clazz, T entity) {
		EntityManager em = getEntityManager();
		EntityTransaction tran = em.getTransaction();
		try {
			tran.begin();
			em.merge(entity);
			tran.commit();
			return entity;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			tran.rollback();
		} finally {
			em.close();
		}
		return null;
	}

	public static <T> void deleteById(Class<T> clazz, Object id) {
		EntityManager em = getEntityManager();
		EntityTransaction tran = em.getTransaction();
		try {
			tran.begin();
			T entity = em.find(clazz, id);
			if (entity != null) {
				em.remove(entity);
			}
			tran.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			tran.rollback();
		} finally {
			em.close();
		}
	}

	public static int count(String entityName) {
		int count = 0;
		EntityManager em = getEntityManager();
		EntityTransaction tran = em.getTransaction();
		try {
			String jpql = "SELECT COUNT(o) FROM " + entityName + " o";
			tran.begin();
			Query query = em.createQuery(jpql);
			Object obj = query.getSingleResult();
			if (obj != null) {
				count = Integer.parseInt(obj.toString());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return count;
	}

	public static EntityManager getEntityManager() {
		return Persistence.createEntityManagerFactory(UNIT_NAME).createEntityManager();
	}

	public static TypedQuery<Object> setParamemter(TypedQuery<Object> query, List<QueryParamemter> queryParamemters) {
		for (QueryParamemter queryParamemter : queryParamemters) {
			if (queryParamemter.getValue() instanceof Date) {
				query.setParameter(queryParamemter.getKey(), (Date) queryParamemter.getValue(),
						queryParamemter.getTemporalType());
			} else if (queryParamemter.getValue() instanceof Calendar) {
				query.setParameter(queryParamemter.getKey(), (Calendar) queryParamemter.getValue(),
						queryParamemter.getTemporalType());
			} else {
				query.setParameter(queryParamemter.getKey(), queryParamemter.getValue());
			}
		}
		return query;
	}

	public static Query setParamemter(Query query, List<QueryParamemter> queryParamemters) {
		for (QueryParamemter queryParamemter : queryParamemters) {
			if (queryParamemter.getKey() == null) {
				query.setParameter(queryParamemter.getPosistion(), queryParamemter.getValue());
			} else {
				query.setParameter(queryParamemter.getKey(), queryParamemter.getValue());
			}
		}
		return query;
	}

	public static StoredProcedureQuery setParamemter(StoredProcedureQuery query,
			List<QueryParamemter> queryParamemters) {
		for (QueryParamemter queryParamemter : queryParamemters) {
			query.setParameter(queryParamemter.getKey(), queryParamemter.getValue());
		}
		return query;
	}
}