package com.mycompany.mavenproject1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;

import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
//RequestScoped
//ManagedBean(name = "calculatorBean")
@Named
//public class CalculatorBean  {
public class CalculatorBean implements Serializable {


    @Inject
    Calculator calculator;

    private String name;
    private String username;
    private double x;
    private double y;
    private double result;
    private String resultl;
    private List<Calculator> users;
    private List<String> users2;

    public CalculatorBean() {
        loadData();
    }



    private void loadData() {
        users = new ArrayList<>();
        users2 = new ArrayList<>();
        //Calculator calculator = users.get(0);
        //setTodo(t.getName());
    }

    public void valueChanged(ValueChangeEvent e) {
        String t = (String) e.getNewValue();
    }


    public List<String> getUsers2() {
        return users2;
    }

    public void setUsers2(List<String> users2) {
        this.users2 = users2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Produces
    public List<Calculator> getUsers() {
        return users;
    }

    public void setUsers(List<Calculator> users) {
        this.users = users;
    }


    public String getResultl() {
        return resultl;
    }

    public void setResultl(String resultl) {
        this.resultl = resultl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String eddU() {


        //calculator.addn(name);
        Calculator cal = new Calculator(name);
        users.add(cal);
        users2.add(cal.getName());

        System.out.println("The Name is = " + name);
        //cal.addn("1");
        //users.add(calculator);

        for (Calculator temp : users) {
            System.out.println(temp.getName());
        }


        resultl = users.toString();
        return "success";
    }

    public String add() {
        result = calculator.add(x, y);
        return "success";
    }


}
