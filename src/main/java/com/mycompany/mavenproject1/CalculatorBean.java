package com.mycompany.mavenproject1;

/**
 *
 * @author ROMAB
 * 
 * https://cloud.mail.ru/public/DgcP/QnJ73Fn2i
 * 
 * 
 * 
 */
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class CalculatorBean {

    
    @Inject
    Calculator calculator;
    
    
    Calculator cal = new Calculator();
    
    List<Calculator> users = new LinkedList<>();
   // private List<Calculator> users;
   
    private String name;
    private double x;
    private double y;
    private double result;
    private String resultl;

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
      
      calculator.addn(name);
      //result = calculator.add(x+10, y+10);
     
      
      cal.addn("1");
       users.add(calculator);
        resultl = users.toString();
        return "success";
    }
    
     public String add() {
        result = calculator.add(x, y);
        return "success";
    }
}
