package com.gitrekt.resort.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bill_items")
public class BillItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "price")
    private Long price;
    
    @Column(name = "quantity")
    private int quantity;
    
    /**
     * DO NOT CALL THIS CONSTRUCTOR. IT IS INTENDED FOR USE BY HIBERNATE ONLY.
     */
    protected BillItem() {
        // Empty pacakage-visible no-arg contructor required for Hibernate.
        name = null; // Prevent compile errors.
        price = null; // Prevent compile errors.
        id = null;
    }
    
    public BillItem(String name, Long price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    
    public String getName() {
        return name;
    }
    
    public Long getPrice() {
        return price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
}
