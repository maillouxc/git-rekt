
package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Package;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Responsible for retrieval, storage, and business logic related specifically to Package objects.
 */
public class PackageService {

    private final EntityManager entityManager;

    public PackageService() {
        this.entityManager = HibernateUtil.getEntityManager();
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();

        entityManager.close();
    }

    /**
     * @return A List of all packages available in the database.
     */
    public List<Package> getAllPackages() {
        String queryString = "FROM Package";
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }

}
