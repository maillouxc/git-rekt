package com.gitrekt.resort.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BillItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private double price;
    
    private int quantity;
    
    /**
     * DO NOT CALL THIS CONSTRUCTOR. IT IS INTENDED FOR USE BY HIBERNATE ONLY.
     */
    protected BillItem() {
        // REQUIRED BY HIBERNATE
    }
    
    public BillItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public double getTotalPrice() {
        return quantity * price;
    }
    
}
