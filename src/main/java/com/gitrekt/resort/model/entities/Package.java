package com.gitrekt.resort.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Package {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private double pricePerPerson;
    
    /**
     * DO NOT CALL THIS CONSTRUCTOR. IT IS MEANT FOR USE ONLY BY HIBERNATE
     */
    Package() {
        // REQUIRED BY HIBERNATE
    }
    
    public Package(String name, double pricePerPerson){
        this.name = name;
        this.pricePerPerson = pricePerPerson;
    }
    
    public String getName(){
        return name;
    }
    
    public double getPricePerPerson(){
        return pricePerPerson;
    }
}
