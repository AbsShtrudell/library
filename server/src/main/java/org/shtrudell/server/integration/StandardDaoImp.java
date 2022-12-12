package org.shtrudell.server.integration;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class StandardDaoImp<T extends Identifiable> extends DaoBase implements StandardDao<T>{

    protected final Class<T> type;

    public StandardDaoImp(Class<T> type) {
        this.type = type;
    }

    public List<T> findAll() {
        EntityManager em =  getEntityManager();
        CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(type);
        Root<T> root = criteriaQuery.from(type);

        criteriaQuery.select(root);

        return em.createQuery(criteriaQuery).getResultList();
    }

    public List<T> findByColumn(String columnName, Object value) {
        if(columnName == null || value == null) return null;

        EntityManager em =  getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(type);
        Root<T> root = query.from(type);

        query.select(root).where(cb.equal(root.get(columnName), value));

        try {
            return em.createQuery(query).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public T findByUniqueColumn(String columnName, Object value) {
        if(columnName == null || value == null) return null;

        EntityManager em =  getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(type);
        Root<T> root = query.from(type);

        query.select(root).where(cb.equal(root.get(columnName), value));

        try {
            return em.createQuery(query).getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

    public T findById(Integer id) {
        if(id == null) return null;

        return findByUniqueColumn("ID", id);
    }

    public T create(T entity) throws PersistenceException {
        if(entity == null) return null;

        EntityManager em = beginTransaction();
        try {
            if (!checkIfExists(entity)) {
                em.persist(entity);
                return entity;
            }
            return null;
        }
        catch (PersistenceException ex) {
            throw new PersistenceException(ex);
        }
        finally {
            commitTransaction();
        }
    }

    public T update(T entity) {
        if(entity == null || entity.getId() == null) return null;

        EntityManager em = beginTransaction();
        try {
            return em.merge(entity);
        } catch (Exception e) {
            return null;
        }
        finally {
            commitTransaction();
        }
    }

    public void delete(T entity) {
        if(entity == null) return;

        EntityManager em = beginTransaction();
        try {
            em.remove(em.contains(entity) ? entity : em.merge(entity));
        }
        finally {
            commitTransaction();
        }
    }

    public void delete(List<T> entities) {
        if(entities == null) return;

        EntityManager em = beginTransaction();
        try {
            for(var entity : entities) {
                if(entity == null) continue;
                em.remove(em.contains(entity) ? entity : em.merge(entity));
            }
        }
        finally {
            commitTransaction();
        }
    }

    public void delete(Integer id) {
        if(id == null) return;

        EntityManager em =  beginTransaction();
        Query query = em.createQuery(String.format("delete from %s where id=:id", type.getName()));
        query.setParameter("id", id);

        try {
            query.executeUpdate();
        } catch (NoResultException | QueryTimeoutException e) {
            System.err.println(e.getMessage());
        }
        finally {
            commitTransaction();
        }
    }

    protected boolean checkIfExists(T example) {
        return false;
    }
}
