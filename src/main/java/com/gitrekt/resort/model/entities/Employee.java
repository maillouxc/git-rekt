package com.gitrekt.resort.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String hashedPassword;

    private boolean isManager = false;

    private String firstName;

    private String lastName;

    private String userName;

    /**
     * DO NOT CALL THIS CONSTRUCTOR. IT IS INTENDED FOR USE BY HIBERNATE ONLY.
     */
    protected Employee() {
        // REQUIRED BY HIBERNATE
    }

    public Employee(String hashedPassword, boolean isManager,
            String firstName, String lastName, String userName) {
        this.hashedPassword = hashedPassword;
        this.isManager = isManager;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }
    
    public void setHashedPassuord(String hashedPassword){
        this.hashedPassword = hashedPassword;
    }

}
