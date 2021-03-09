package com.arnoldgalovics.batchingpractice.support;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class Options {
    @PersistenceContext
    private EntityManager entityManager;

    public int globalBatchSize() {
        return entityManager.getEntityManagerFactory().unwrap(SessionFactory.class).getSessionFactoryOptions().getJdbcBatchSize();
    }
}
