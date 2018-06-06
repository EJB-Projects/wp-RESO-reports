package ru.reso.wp.web.utils;

import javax.faces.FactoryFinder;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;




/**
 * JSF utilities.
 *
 * @author nicole
 */
public class FacesUtils {


    /**
     * Get managed bean based on the bean name.
     *
     * @param beanName the bean name
     * @return the managed bean associated with the bean name
     */
    public static Object getManagedBean(String beanName) {

        return getValueBinding(getJsfEl(beanName)).getValue(FacesContext.getCurrentInstance());
    }

    /**
     * Value binding
     *
     * @param el
     * @return
     */
    private static ValueBinding getValueBinding(String el) {
        return getApplication().createValueBinding(el);
    }

    /**
     * Application
     *
     * @return
     */
    private static Application getApplication() {
        ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        return appFactory.getApplication();
    }

    /**
     * JSF EL
     *
     * @param value
     * @return
     */
    private static String getJsfEl(String value) {
        return "#{" + value + "}";
    }



}
