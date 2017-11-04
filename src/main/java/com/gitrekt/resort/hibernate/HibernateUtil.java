package com.gitrekt.resort.hibernate;

import com.github.fluent.hibernate.cfg.strategy.hibernate5.Hibernate5NamingStrategy;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    
    private static final String PERSISTENCE_UNIT_NAME = "com.gitrekt.resort";
    
    private static final EntityManagerFactory entityManagerFactory = 
        Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            
            
    
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
    
    private static EntityManagerFactory createEntityManagerFactory() {
        Configuration c = new Configuration();
        c.addFile("src/main/resources/META-INF/persistence.xml");
        c.setImplicitNamingStrategy(new Hibernate5NamingStrategy());
        System.out.println(c.getProperties().toString());
        
        Properties properties = c.getProperties();
        Map<String, String> pMap = new HashMap<>();
        Enumeration<?> e = properties.propertyNames();
        while (e.hasMoreElements()) {
            String s = (String) e.nextElement();
            pMap.put(s, properties.getProperty(s));
        }
        
        return Persistence.createEntityManagerFactory(
            PERSISTENCE_UNIT_NAME, pMap
        );
    }
    
}
