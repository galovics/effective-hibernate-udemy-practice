package com.arnoldgalovics.batchingpractice.service;

import com.arnoldgalovics.batchingpractice.domain.Company;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

/**
 * A simple Spring component that acts as a layer on top of the pure database operations.
 */
@Component
public class CompanyService {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates companies in the database.
     *
     * @param newCompanies the companies to be created
     */
    @Transactional
    public void create(Collection<Company> newCompanies) {
        for (Company company : newCompanies) {
            entityManager.persist(company);
        }
    }

    /**
     * Resets the productCount value to 0 for the specified companies identified by their ids.
     *
     * @param companyIds the ids of the companies
     */
    @Transactional
    public void resetProductCounts(Collection<Integer> companyIds) {
        TypedQuery<Company> query = entityManager.createQuery("FROM Company c WHERE c.id IN (:companyIds)", Company.class);
        query.setParameter("companyIds", companyIds);
        Collection<Company> companies = query.getResultList();
        for (Company company : companies) {
            company.setProductCount(company.getProductCount() - 1);
        }
    }
}
