package com.gitrekt.resort.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    
    private static final String PERSISTENCE_UNIT_NAME = "com.gitrekt.resort";
    
    private static final EntityManagerFactory entityManagerFactory = 
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
    
}
