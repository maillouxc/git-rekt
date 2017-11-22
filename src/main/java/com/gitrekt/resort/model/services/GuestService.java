package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Guest;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class GuestService {

//    private final EntityManager entityManager;
//
//    public GuestService() {
//        this.entityManager = HibernateUtil.getEntityManager();
//    }
//
//    /**
//     * Takes care of closing the Hibernate entityManager for the class.
//     *
//     * @throws Throwable
//     */
//    @Override
//    public void finalize() throws Throwable {
//        super.finalize();
//        this.entityManager.close();
//    }

    public List<Guest> getCurrentlyCheckedInGuests() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        String query = "FROM Guest WHERE isCheckedIn = :param";
        Query q = entityManager.createQuery(query);
        q.setParameter("param", true);
        List<Guest> results = q.getResultList();
        entityManager.close();
        return results;
    }

    public Guest getGuestById(Long id) throws EntityNotFoundException {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        Guest guest = entityManager.getReference(Guest.class, id);
        entityManager.close();
        return guest;
    }

    public Guest getGuestByEmailAddress(String emailAddress) throws EntityNotFoundException {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        String query = "FROM Guest WHERE emailAddress = :emailAddress";
        Query q = entityManager.createQuery(query);
        q.setParameter("emailAddress", emailAddress);
        Guest result = (Guest) q.getSingleResult();
        entityManager.close();
        return result;
    }

    public void createNewGuest(Guest guest) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(guest);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            // TODO: Log rollback or notify user somewhere, possibly in e.
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public void updateGuest(Guest guest) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(guest);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            // TODO: Log rollback or notify user somewhere, possibly in e.
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
