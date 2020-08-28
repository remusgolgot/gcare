package com.gcare.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class GenericDAO {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public <E> E insert(E entity) {
        em.persist(entity);
        return entity;
    }

    @Transactional
    public <E> E merge(E entity) {
        em.merge(entity);
        return entity;
    }

    @Transactional
    public <E> E update(E entity) {
        em.merge(entity);
        return entity;
    }

    @Transactional
    public void delete(Object entity) {
        em.remove(entity);
    }

    @Transactional
    public <E> List get(Class<E> clazz) {
        return em.createQuery(
                "SELECT c FROM " + clazz.getSimpleName() + " c ")
                .getResultList();
    }

    @Transactional
    public <E> void deleteAll(Class<E> clazz) {
        em.createQuery(
                "Delete FROM " + clazz.getSimpleName()).executeUpdate();
    }

    @Transactional
    public <E> int count(Class<E> clazz) {
        return em.createQuery(
                "SELECT COUNT(*) FROM " + clazz.getSimpleName())
                .getFirstResult();
    }

    @Transactional
    public <E> List<E> list(Class<E> clazz) {
        return em.createQuery(
                "SELECT c FROM " + clazz.getSimpleName() + " c ")
                .getResultList();
    }

}
