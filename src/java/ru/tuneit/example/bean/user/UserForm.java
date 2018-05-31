package ru.tuneit.example.bean.user;

import java.io.Serializable;

/**
 * @author nicola
 * @version 1.0 (Aug 15, 2011)
 */
public class UserForm implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String firstName = "";
    
    private String secondName = "";
    
    private Long age = null;
    
    private String address = "";

    public UserForm() {
    }

    public UserForm(String firstName, String secondName, Long age, String address) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    
    public void reset() {
        firstName = "";
        secondName = "";
        age = null;
        address = "";
    }
    
    
    
}
