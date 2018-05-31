/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common;

import javax.ejb.EJB;
import javax.enterprise.inject.Produces;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ROMAB
 */
@RequestScoped
@Named
public class WebManager {
 
   @EJB
    private helloService hellService;

   @Inject
   private HelloBean user;

   @Produces
    //@Named
    //@RequestScoped
    public HelloBean getBid() {
        return user;
    }

    public String addUser() {
        hellService.add(user);
        
        return "welcome.xhtml";
    }
    
}
