package com.gitrekt.resort.model.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.mindrot.jbcrypt.BCrypt;

@Entity
public class Employee {

    // Employee ID is not auto-generated, but rather would already exist in the
    // business's records.
    @Id
    private Long employeeId;

    private String hashedPassword;

    private boolean isManager = false;

    private String firstName;

    private String lastName;
    
    private static final int HASH_ITERATIONS = 2000;

    /**
     * DO NOT CALL THIS CONSTRUCTOR. IT IS INTENDED FOR USE BY HIBERNATE ONLY.
     */
    protected Employee() {
        // REQUIRED BY HIBERNATE
    }

    public Employee(String plaintextPassword, boolean isManager,
            String firstName, String lastName) {
        encryptPassword(plaintextPassword);
        this.isManager = isManager;
        this.firstName = firstName;
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
    
    public void setHashedPassword(String hashedPassword){
        this.hashedPassword = hashedPassword;
    }
    
    private void encryptPassword(String plaintextPassword) {
        int numRoundsToHash = 2000;
        String salt = BCrypt.gensalt(HASH_ITERATIONS);
        this.hashedPassword = BCrypt.hashpw(plaintextPassword, salt);
    }

}
