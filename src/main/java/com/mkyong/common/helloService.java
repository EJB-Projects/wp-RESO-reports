/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ROMAB
 */
@Local
public interface helloService {
    
    public void add(HelloBean hello);
    
   public List<HelloBean> getAllHellos();  
    
}
