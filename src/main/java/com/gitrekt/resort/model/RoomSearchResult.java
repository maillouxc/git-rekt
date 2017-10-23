package com.gitrekt.resort.model;

import java.math.BigDecimal;

/**
 * TODO: Document entire class.
 */
public class RoomSearchResult {
    
    private String roomNumber;
    
    private BigDecimal roomPrice;
    
    private RoomCategory roomCategory;
    
    public RoomSearchResult(
        String roomNumber, BigDecimal roomPrice, RoomCategory roomCategory
    ) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomCategory = roomCategory;
    }
    
    public RoomCategory getRoomCategory() {
        return roomCategory;
    }
    
    public BigDecimal getRoomPrice() {
        return roomPrice;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
}
