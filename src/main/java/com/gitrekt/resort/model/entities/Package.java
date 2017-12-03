package com.gitrekt.resort.model.entities;

import java.util.Objects;
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

    private String imagePath;

    private String description;

    /**
     * DO NOT CALL THIS CONSTRUCTOR. IT IS MEANT FOR USE ONLY BY HIBERNATE.
     */
    Package() {
        // REQUIRED BY HIBERNATE
    }

    public Package(String name, double pricePerPerson, String imagePath, String description) {
        this.name = name;
        this.pricePerPerson = pricePerPerson;
        this.imagePath = imagePath;
        this.description = description;
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

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Package other = (Package) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
