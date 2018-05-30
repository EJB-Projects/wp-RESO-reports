package com.actionbazaar.persistence;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;

/**
 * Represents a user
 */
public class User implements Serializable {

   
    private String firstName;
    private String lastName;

    
    public User() {
        // empty
    }


    
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName.toUpperCase();
    }

    
    public String getLastName() {
        return lastName;
    }

    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
}
