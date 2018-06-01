package com.mycompany.mavenproject1;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import java.io.Serializable;

@Stateful
//public class Calculator {
    public class Calculator implements Serializable{

    private String Name;

    public double add(double x, double y) {
        return x + y;
    }

    public void addn(String name) {

        this.Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Calculator(String Name) {
        this.Name = Name;
    }

    public Calculator() {
        this.Name = "1";
    }


}