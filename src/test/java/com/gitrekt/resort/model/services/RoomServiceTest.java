package com.gitrekt.resort.model.services;

import com.gitrekt.resort.DatabaseTestDataLoader;
import com.gitrekt.resort.model.entities.Room;
import com.gitrekt.resort.model.entities.RoomCategory;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RoomServiceTest {

    public RoomServiceTest() {
        // TODO
    }

    @BeforeClass
    public static void setUpClass() {
        DatabaseTestDataLoader.initializeTestData();
    }

    @AfterClass
    public static void tearDownClass() {
        // TODO
    }

    @Before
    public void setUp() {
        // TODO
    }

    @After
    public void tearDown() {
        // TODO
    }

    /**
     * Test of cleanup method, of class RoomService.
     */
    @Test(expected = IllegalStateException.class)
    public void testCleanup() {
        System.out.println("cleanup");
        RoomService instance = new RoomService();
        instance.cleanup();
        instance.getAllRooms();
    }

    /**
     * Test of getAllRooms method, of class RoomService.
     *
     * Instead of having to test the contents of each room, merely tests the quantity of rooms
     * returned to be equal to the expected amount.
     */
    @Test
    public void testGetAllRooms() {
        System.out.println("getAllRooms");
        RoomService instance = new RoomService();
        List<Room> result = instance.getAllRooms();
        assertEquals("Expected to get 200 rooms", result.size(), 200);
    }

    /**
     * Test of getAllRoomsInCategory method, of class RoomService.
     *
     * Simply tests the size of the result, rather than the contents. Probably good for most cases.
     */
    @Test
    public void testGetAllRoomsInCategory() {
        System.out.println("getAllRoomsInCategory");
        String categoryName = "King";
        RoomService instance = new RoomService();
        List<Room> result = instance.getAllRoomsInCategory(categoryName);
        assertEquals("Expected to receieve get 10 King rooms", result.size(), 10);
    }

    /**
     * Test of getAllRoomCategories method, of class RoomService.
     *
     * Simply tests the size of the result, rather than the contents. Probably good for most cases.
     */
    @Test
    public void testGetAllRoomCategories() {
        System.out.println("getAllRoomCategories");
        RoomService instance = new RoomService();
        List<RoomCategory> result = instance.getAllRoomCategories();
        int numTestCategories = 5;
        assertEquals(result.size(), numTestCategories);
    }

    /**
     * Test of getCurrentRoomPrice method, of class RoomService.
     *
     * Checks that the price of a basic category test room is equal to the expected value of 100.00,
     * within a tolerance of 0.0001;
     */
    @Test
    public void testGetCurrentRoomPrice() {
        System.out.println("getCurrentRoomPrice");
        RoomService instance = new RoomService();
        String categoryName = "Basic";
        RoomCategory roomCategory = instance.getRoomCategoryByName(categoryName);
        Double expResult = 100.00;
        Double result = instance.getCurrentRoomPrice(roomCategory);
        assertEquals("Price of basic room was expected to be 100.00 within +- 0.0001",
                expResult, result, 0.0001);
    }

}
