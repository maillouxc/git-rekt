package com.gitrekt.resort.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "packages")
public class Package {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "price_per_person")
    private double pricePerPerson;
    
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
