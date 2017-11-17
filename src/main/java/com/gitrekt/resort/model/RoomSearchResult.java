package com.gitrekt.resort.model;

import com.gitrekt.resort.model.entities.RoomCategory;

public class RoomSearchResult {
    
    private final RoomCategory roomCategory;
    
    private final Double roomPrice;
    
    public RoomSearchResult(double roomPrice, RoomCategory roomCategory
    ) {
        this.roomCategory = roomCategory;
        this.roomPrice = roomPrice;
    }
    
    public RoomCategory getRoomCategory() {
        return roomCategory;
    }
    
    public Double getRoomPrice() {
        return roomPrice;
    }
    
}
