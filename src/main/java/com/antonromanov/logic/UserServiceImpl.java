/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antonromanov.logic;

import com.antonromanov.persistence.UserEnt;
import java.util.ArrayList;
import javax.ejb.Stateful;

/**
 *
 * @author ROMAB
 */

@Stateful
public class UserServiceImpl implements UserService{

   private ArrayList<UserEnt> personsList = new ArrayList<UserEnt>();
    @Override
    public String doPost() {
       return "Секс";
    }

    @Override
    public void addUser(UserEnt user) {
     
      personsList.add(user);
    }
    
    
    
}
