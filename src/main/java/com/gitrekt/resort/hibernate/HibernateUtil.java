package com.gitrekt.resort.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Utility class used to manage Hibernate and get access to entityManager instances.
 */
public class HibernateUtil {

    private static final String PERSISTENCE_UNIT_NAME = "com.gitrekt.resort";

    private static final EntityManagerFactory entityManagerFactory =
        Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void close() {
        entityManagerFactory.close();
    }
}
