package com.gcare.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class GenericDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public <E> E insert(E entity) {
        em.persist(entity);
        return entity;
    }

    @Transactional
    public <E> E update(E entity) {
        return em.merge(entity);
    }

    @Transactional
    public void delete(Object entity) {
        em.remove(entity);
    }


}
