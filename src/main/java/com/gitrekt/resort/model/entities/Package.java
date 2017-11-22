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

    public Long getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public double getPricePerPerson(){
        return pricePerPerson;
    }

    /**
     * Calculates Package equality based on database ID and package name. Does NOT account for
     * package price per person in it's equality calculations.
     *
     * @param obj The object to compare against.
     *
     * @return True if the two packages are equal.
     */
    @Override
    public boolean equals(Object obj) {
        Package p = (Package) obj;
        if(!name.equals(p.getName())) {
            return false;
        }
        if(id != p.getId()) {
            return false;
        }
        return true;
    }
}
