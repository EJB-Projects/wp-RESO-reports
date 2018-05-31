/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

/**
 *
 * @author ROMAB
 */
@Stateless
public class HelloServiceImpl implements helloService{

    List<HelloBean> beans = new LinkedList<HelloBean>();
    private List<HelloBean> users;
    
  /*  @PostConstruct
  private void init(){
    users = new ArrayList<HelloBean>();
  }
        
    */

    /**
     *
     * @param hello
     */

    
    @Override
    public void add(HelloBean hello) {
        
         users.add(hello);
       
        
    }

    @Override
    public List<HelloBean> getAllHellos() {
        return beans;
    }
    
}
