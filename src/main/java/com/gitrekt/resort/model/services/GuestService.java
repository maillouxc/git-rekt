package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Guest;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

public class GuestService {

    public List<Guest> getCurrentlyCheckedInGuests() {
        EntityManager entityManager = HibernateUtil.getEntityManager();

        return null;
    }

    public Guest getGuestById(Long id) {
        EntityManager entityManager = HibernateUtil.getEntityManager();

        return null;
    }

    public Guest getGuestByEmailAddress(String emailAddress) {
        EntityManager entityManager = HibernateUtil.getEntityManager();

        return null;
    }

    public void createNewGuest(Guest guest) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.getTransaction().commit();
            entityManager.persist(guest);

        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();

        }
        entityManager.close();
    }

    public void updateGuest(Guest guest) {
        EntityManager entityManager = HibernateUtil.getEntityManager();

    }
}
