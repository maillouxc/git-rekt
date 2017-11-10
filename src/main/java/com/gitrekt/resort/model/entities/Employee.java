package com.gitrekt.resort.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long employeeId;
    
    String hashedPassword;
    
    boolean isManager;
    
    String firstName;
    
    String lastName;

     /**
     * DO NOT CALL THIS CONSTRUCTOR. IT IS INTENDED FOR USE BY HIBERNATE ONLY.
     */
    protected Employee() {
        // REQUIRED BY HIBERNATE
    }
    
    public Employee(String hashedPassword, boolean isManager,
            String firstNamw, String lastName) {
        this.hashedPassword = hashedPassword;
        this.isManager = isManager;
        this.firstName = firstNamw;
        this.lastName = lastName;
    }

    public Long getId() {
        return employeeId;
    }

    public boolean isManager() {
        return isManager;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }
}
