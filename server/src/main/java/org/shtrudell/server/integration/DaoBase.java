package org.shtrudell.server.integration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public abstract class DaoBase {
    protected static EntityManagerFactory emFactory;

    protected final ThreadLocal<EntityManager> threadLocalEntityManager = new ThreadLocal<>();

    protected DaoBase() {
        if(emFactory == null)
            emFactory = Persistence.createEntityManagerFactory("Library");
    }

    public void update() {
        commitTransaction();
    }

    protected EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }

    protected EntityManager beginTransaction() {
        EntityManager em = emFactory.createEntityManager();
        threadLocalEntityManager.set(em);
        EntityTransaction transaction = em.getTransaction();
        if(!transaction.isActive()) {
            transaction.begin();
        }
        return em;
    }

    protected void commitTransaction() {
        threadLocalEntityManager.get().getTransaction().commit();
    }
}
