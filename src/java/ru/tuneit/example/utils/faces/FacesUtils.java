/*
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * "The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations under
 * the License.
 *
 * The Original Code is ICEfaces 1.5 open source software code, released
 * November 5, 2006. The Initial Developer of the Original Code is ICEsoft
 * Technologies Canada, Corp. Portions created by ICEsoft are Copyright (C)
 * 2004-2006 ICEsoft Technologies Canada, Corp. All Rights Reserved.
 *
 * Contributor(s): _____________________.
 *
 * Alternatively, the contents of this file may be used under the terms of
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"
 * License), in which case the provisions of the LGPL License are
 * applicable instead of those above. If you wish to allow use of your
 * version of this file only under the terms of the LGPL License and not to
 * allow others to use your version of this file under the MPL, indicate
 * your decision by deleting the provisions above and replace them with
 * the notice and other provisions required by the LGPL License. If you do
 * not delete the provisions above, a recipient may use your version of
 * this file under either the MPL or the LGPL License."
 *
 */
package ru.tuneit.example.utils.faces;

import ru.tuneit.example.bean.locale.LocaleBean;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 * JSF utilities.
 */
public class FacesUtils {

    private static final Logger _log = Logger.getLogger(FacesUtils.class.getName());


    /**
     * Get managed bean based on the bean name.
     *
     * @param beanName the bean name
     * @return the managed bean associated with the bean name
     */
    public static Object getManagedBean(String beanName) {
        return getValueBinding(getJsfEl(beanName)).getValue(FacesContext.getCurrentInstance());
    }

    /*
    public static HttpServletRequest getHttpRequest() {
        return PortalUtil.getOriginalServletRequest((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(PortletServlet.PORTLET_SERVLET_REQUEST));
    }*/

    /**
     * Remove the managed bean based on the bean name.
     *
     * @param beanName the bean name of the managed bean to be removed
     */
    public static void resetManagedBean(String beanName) {
        getValueBinding(getJsfEl(beanName)).setValue(FacesContext.getCurrentInstance(), null);
    }

    /**
     * Store the managed bean inside the session scope.
     *
     * @param beanName    the name of the managed bean to be stored
     * @param managedBean the managed bean to be stored
     */
    /*
    public static void setManagedBeanInSession(String beanName, Object managedBean) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(beanName, managedBean);
    }
*/
    /**
     * Get parameter value from request scope.
     *
     * @param name the name of the parameter
     * @return the parameter value
     */
    public static String getRequestParameter(String name) {
        return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
    }

    protected static ClassLoader getCurrentClassLoader(Object defaultObject) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        if (loader == null) {
            loader = defaultObject.getClass().getClassLoader();
        }

        return loader;
    }

    public static Object getVariable(String name) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(name);
    }

    public static String getLocalizedMessage(String key) {
        return getLocalizedMessage(key, null);
    }

    public static String getLocalizedMessage(String key, Object params[]) {
        FacesContext context = FacesContext.getCurrentInstance();

        LocaleBean localeBean = (LocaleBean) getManagedBean("localeBean");
        return getLocalizedMessage(context.getApplication().getMessageBundle(),
                key, params, localeBean.getUsedLocale());
    }
    public static String getLocalizedMessage(String bundleName, String key,
            Object params[], Locale locale) {        
        
        _log.debug("LOCALE = " + locale + " bundleName = " + bundleName + " key = " + key + " params = " + params);

        String text = null;
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale, getCurrentClassLoader(params));

        try {
            text = bundle.getString(key);
        } catch (MissingResourceException e){
            text = "?? key " + key + " not found ??";
        }

        if (params != null){
            MessageFormat mf = new MessageFormat(text, locale);
            text = mf.format(params, new StringBuffer(), null).toString();
        }

        return text;
    }

    public static String getActionAttribute(ActionEvent event, String name) {
        Object o = event.getComponent().getAttributes().get(name);
        return (o != null ? o.toString() : null);
    }


    /**
     * Add information message.
     *
     * @param msg the information message
     */
    public static void addInfoMessage(String msg) {
        addInfoMessage(null, msg);
    }

    /**
     * Add information message to a specific client.
     *
     * @param clientId the client id
     * @param msg      the information message
     */
    public static void addInfoMessage(String componentId, String msg) {
        FacesContext.getCurrentInstance()
                .addMessage(getComponentsClientId(componentId), new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
    }

    /**
     * Add error message.
     *
     * @param msg the error message
     */
    public static void addErrorMessage(String msg) {
        addErrorMessage(null, msg);
    }

    /**
     * Add error message to a specific client.
     *
     * @param clientId the client id
     * @param msg      the error message
     */
    public static void addErrorMessage(String componentId, String msg) {
        FacesContext.getCurrentInstance()
                .addMessage(getComponentsClientId(componentId), new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
    }

    private static Application getApplication() {
        ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        return appFactory.getApplication();
    }
    
    /**
     * @deprecated
     * @param el
     * @return 
     */
    private static ValueBinding getValueBinding(String el) {
        return getApplication().createValueBinding(el);
    }
    
    private static String getJsfEl(String value) {
        return "#{" + value + "}";
    }

    /**
     * Refresh view tree
     */
    public static void refresh() {
        FacesContext context = FacesContext.getCurrentInstance();
        ViewHandler viewHandler = context.getApplication().getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context
            .getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
    }

    /**
     * Returns the clientId for a component with the given id.
     */
    public static String getComponentsClientId(String componentId) {
        if (componentId == null) {
            return null;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        UIComponent c = findComponent(root, componentId);
        return c.getClientId(context);
    }

    /**
     * @param componentId
     * @return UIComponent with the given componentId
     */
    public static UIComponent getComponent(String componentId) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        return findComponent(root, componentId);
    }

    /**
     * Finds component with the given id
     */
    private static UIComponent findComponent(UIComponent c, String id) {
        if (id.equals(c.getId())) {
            return c;
        }
        Iterator<UIComponent> kids = c.getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent found = findComponent(kids.next(), id);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
    
    public static String getComponentClientId(String componentId) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        UIComponent c = findComponent(root, componentId);
        return c.getClientId(context);
    }
    
    public static SelectItem getSelectItemByValue(List<SelectItem> listItems, Object value) throws IllegalArgumentException{
        SelectItem result = null;
        if (listItems != null && listItems.size() > 0) {
            try {
                for (SelectItem item : listItems) {
                    if (item.getValue().equals(value)) {
                        result = item;
                        break;
                    }
                }
            } catch (Exception e) {
                _log.error(e);
            }
        }
        if (result == null) {
            throw new IllegalArgumentException("Value '" + value + "' not found in list items !");
        }
        return result;
    }

    public static Object getValueSelectItemByLabel(List<SelectItem> items, String label) {
        if (items != null) {
            for (SelectItem item : items) {
                if (item.getLabel().compareToIgnoreCase(label) == 0) {
                    return item.getValue();
                }
            }
        }

        return null;
    }


    

}
