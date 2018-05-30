/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antonromanov.web;

import com.antonromanov.logic.UserService;
import com.antonromanov.persistence.UserEnt;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ROMAB
 */
@Named
public class UserManager {
    
   @EJB
    private UserService userService;
   
   @Inject
    private UserEnt user;
   
   
   @Produces
    @Named
    @RequestScoped
    public UserEnt getUser() {
        return user;
    }
   
  public String addUser() {
        
        userService.addUser(user);
        return "bid_confirm.xhtml";
    } 
    
}
