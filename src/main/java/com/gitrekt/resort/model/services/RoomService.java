package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Room;
import com.gitrekt.resort.model.entities.RoomCategory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * No, not that kind of room service.
 */
public class RoomService {

    private final EntityManager entityManager;

    /**
     * Creates an instance of the service class, along with it's associated entityManager.
     */
    public RoomService(){
        this.entityManager = HibernateUtil.getEntityManager();
    }

    /**
     * Takes care of closing the Hibernate entityManager for the class.
     *
     * @throws Throwable
     */
    @Override
    public void finalize() throws Throwable {
        super.finalize();
        this.entityManager.close();
    }

    public void cleanup() {
        entityManager.close();
    }

    public List<Room> getAllRooms() {
        String queryString = "FROM Room";
        Query q = entityManager.createQuery(queryString);
        return q.getResultList();
    }

    public List<Room> getAllRoomsInCategory(String categoryName) {
        String query = "FROM Room WHERE roomCategory.name = :categoryName";
        Query q = entityManager.createQuery(query);
        q.setParameter("categoryName", categoryName);
        return q.getResultList();
    }

    public List<RoomCategory> getAllRoomCategories() {
        return entityManager.createQuery("FROM RoomCategory").getResultList();
    }

    /**
     * This method should be used instead of the getPrice() method in roomCategory.
     *
     * This should probably be handled in a better way, but it all comes down to the amount of
     * design time available in the end, and that is a resource we are critically short on at the
     * moment.
     *
     * @param roomCategory The category of room to determine the price for.
     *
     * @return The room price after pricing adjustments are taken into account.
     */
    public Double getCurrentRoomPrice(RoomCategory roomCategory) {
        Double basePrice = roomCategory.getBasePrice();
        Double currentPrice = basePrice;
        // TODO: Factor resort capacity into pricing
        return currentPrice;
    }

    public RoomCategory getRoomCategoryByName(String categoryName) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        String queryString = "FROM RoomCategory WHERE name = :categoryName";
        Query q = entityManager.createQuery(queryString);
        q.setParameter("categoryName", categoryName);
        RoomCategory result = (RoomCategory) q.getSingleResult();
        entityManager.close();
        return result;
    }

}
