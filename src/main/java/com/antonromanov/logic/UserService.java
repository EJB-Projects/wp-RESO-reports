/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antonromanov.logic;

import com.actionbazaar.persistence.User;
import com.antonromanov.persistence.UserEnt;
import javax.ejb.Local;

/**
 *
 * @author ROMAB
 */
@Local
public interface UserService {
    
    public String doPost();
    public void addUser(UserEnt user);
    
}
