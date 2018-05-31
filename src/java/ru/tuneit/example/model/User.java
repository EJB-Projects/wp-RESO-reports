package ru.tuneit.example.model;

import java.io.Serializable;
import ru.tuneit.example.bean.user.UserForm;

/**
 * @author nicola
 * @version 1.0 (Jun 20, 2011)
 */
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String firstName;
    
    private String secondName;
    
    private Long age;
    
    private String address;
    
    private boolean selected;

    public User(UserForm userForm) {
        this(userForm.getFirstName(), userForm.getSecondName(), userForm.getAge(), userForm.getAddress());
    }
    
    public User(String firstName, String secondName, Long age, String address) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.address = address;
        this.selected = false;
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

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if ((this.firstName == null) ? (other.firstName != null) : !this.firstName.equals(other.firstName)) {
            return false;
        }
        if ((this.secondName == null) ? (other.secondName != null) : !this.secondName.equals(other.secondName)) {
            return false;
        }
        if (this.age != other.age && (this.age == null || !this.age.equals(other.age))) {
            return false;
        }
        if ((this.address == null) ? (other.address != null) : !this.address.equals(other.address)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.firstName != null ? this.firstName.hashCode() : 0);
        hash = 23 * hash + (this.secondName != null ? this.secondName.hashCode() : 0);
        hash = 23 * hash + (this.age != null ? this.age.hashCode() : 0);
        hash = 23 * hash + (this.address != null ? this.address.hashCode() : 0);
        return hash;
    }
    
    
}
