package org.shtrudell.server.integration;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public interface StandardDao<T> {
    public List<T> findAll();

    public T findByUniqueColumn(String columnName, Object value);

    public T findById(Integer id);

    public void create(T entity) throws PersistenceException;

    public T update(T entity);

    public void delete(T entity);

    public void delete(List<T> entities);

    public void delete(Integer id);
}
