package com.gitrekt.resort.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.mindrot.jbcrypt.BCrypt;

@Entity
public class Employee {

    // Employee ID is not auto-generated, but rather would already exist in business records.
    @Id
    private Long employeeId;

    private String hashedPassword;

    private boolean isManager = false;

    private String firstName;

    private String lastName;

    private static final int LOG_ROUNDS = 10; // Used for password encryption

    /**
     * DO NOT CALL THIS CONSTRUCTOR. IT IS INTENDED FOR USE BY HIBERNATE ONLY.
     */
    protected Employee() {
        // REQUIRED BY HIBERNATE
    }

    public Employee(Long id, String plaintextPassword, boolean isManager,
            String firstName, String lastName) {

        encryptPassword(plaintextPassword);

        this.isManager = isManager;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = id;
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

    public void setPassword(String plaintextPassword){
        encryptPassword(plaintextPassword);
    }

    private void encryptPassword(String plaintextPassword) {
        String salt = BCrypt.gensalt(LOG_ROUNDS);
        this.hashedPassword = BCrypt.hashpw(plaintextPassword, salt);
    }

}
