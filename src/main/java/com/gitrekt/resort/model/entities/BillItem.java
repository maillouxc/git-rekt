package com.gitrekt.resort.model.entities;

public class BillItem {
    
    private final String name;
    
    private final Long price;
    
    private int quantity;
    
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
