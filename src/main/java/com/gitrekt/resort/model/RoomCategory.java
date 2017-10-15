package com.gitrekt.resort.model;

import javafx.scene.image.Image;

/**
 * TODO: Document class before merging into development branch.
 */
public class RoomCategory {
    
    private String name;
    
    private String description;
    
    private Image roomCategoryImage;
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    // We may want to modify this so that we use S3 to fetch images, but that
    // is a future problem that we don't need to solve anytime soon. This is 
    // good enough for now.
    public Image getImage() {
        return roomCategoryImage;
    }
    
}
