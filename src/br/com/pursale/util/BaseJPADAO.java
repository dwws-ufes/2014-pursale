package br.com.pursale.util;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class BaseJPADAO<T extends Serializable> implements BaseDAO<T> {
	protected abstract EntityManager getEntityManager();
	
	protected abstract Class<T> getDomainClass();

	@Override
	public long retrieveCount() {
		
		EntityManager em = getEntityManager();
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		Root<T> rt = cq.from(getDomainClass());
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
	
		return ((Long)q.getSingleResult()).longValue();
	}

	@Override
	public List<T> retrieveAll() {
		
		EntityManager em = getEntityManager();
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(getDomainClass()));
		
		return em.createQuery(cq).getResultList();
	}

	@Override
	public List<T> retrieveSome(int[] interval) {
		
		EntityManager em = getEntityManager();
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(getDomainClass()));
		
		Query q = em.createQuery(cq);
		q.setMaxResults(interval[1] - interval[0]);
		q.setFirstResult(interval[0]);
		
		return q.getResultList();
	}

	@Override
	public T retrieveById(Long id) {
		EntityManager em = getEntityManager();
		return (T) em.find(getDomainClass(), id);
	}

	@Override
	public T save(T object) {
		EntityManager em = getEntityManager();
		object = em.merge(object);
		return object;
	}

	@Override
	public void delete(T object) {
		EntityManager em = getEntityManager();
		em.remove(em.merge(object));
	}
}
