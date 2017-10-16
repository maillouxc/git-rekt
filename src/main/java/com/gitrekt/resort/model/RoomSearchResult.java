package com.gitrekt.resort.model;

/**
 * TODO: Document entire class.
 */
public class RoomSearchResult {
    
    private String roomNumber;
    
    private String roomPrice;
    
    private RoomCategory roomCategory;
    
    public RoomCategory getRoomCategory() {
        return roomCategory;
    }
    
    public String getRoomPrice() {
        return roomPrice;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
}
