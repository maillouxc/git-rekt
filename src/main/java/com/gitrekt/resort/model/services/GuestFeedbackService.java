package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.GuestFeedback;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class GuestFeedbackService {

    private final EntityManager entityManager;

    public GuestFeedbackService() {
        this.entityManager = HibernateUtil.getEntityManager();
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
        this.entityManager.close();
    }

    /**
     * @return All guest feedback reports from the database that are not yet marked resolved.
     */
    public List<GuestFeedback> getUnresolvedGuestFeedback() {
        String query = "FROM GuestFeedback WHERE isResolved = :param";
        Query q = entityManager.createQuery(query);
        q.setParameter("param", false);
        return q.getResultList();
    }

    public void createNewGuestFeedback(GuestFeedback feedback) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(feedback);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public void updateGuestFeedback(GuestFeedback feedback) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(feedback);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

}
