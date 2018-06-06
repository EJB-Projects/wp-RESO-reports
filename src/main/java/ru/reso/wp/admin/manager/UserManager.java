package ru.reso.wp.admin.manager;

import ru.reso.wp.admin.users.User;

import javax.enterprise.inject.Produces;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserManager {


    @Inject
    private User user;


    @Produces
    @Named
    @RequestScoped
    public User getUser() {
        return user;
    }


}
