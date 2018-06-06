/*
 * Document: UserSessionController
 * Content: Главный  класс обслуживающий сессию пользователя в ЛК.
 * Created on: 22.09.2010, 14:51:57
 * Author: Nicole
 * Description: Главный класс, хранит все данные в пределах сессии пользователя.
 *              Информацию о самом пользователе.
 */

package ru.reso.wp.web.administrator.controller;

import ru.reso.wp.admin.users.User;
import ru.reso.wp.web.consts.Consts;
import ru.reso.wp.web.consts.Notes;
import ru.reso.wp.web.sections.reports.ResoManagedBean;
import ru.reso.wp.admin.manager.UserManager;

import javax.annotation.PostConstruct;
import javax.ejb.NoSuchEJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.SQLException;

/**
 * Сессия пользователя в онлайн "Рабочее место"
 *
 * @author Nicole
 */
@ManagedBean
@SessionScoped
public class UserSessionController extends ResoManagedBean implements Serializable {


    /**
     * Менеджер пользователей ЛК
     */
    private UserManager userManager;

    /**
     * Сообщение. Ошибка.
     */
    private String errorMessage = "";

    /**
     * Подсистема сообщений
     */
   // private MessageSystem messages;

    /**
     * EJB. "Авторизация" Session StateFull (WebLogic). Для каждого пользователя
     * свой.
     */
   // private EJBUserSessionRemote ejbUserSession = null;

    /**
     * Веб якорь
     */
    private String wpanchor = "#";
    /**
     * Текущее дествие пользователя
     */
    private Consts.actions action = Consts.actions.logout;


    /**
     * Constructor
     *
     * @throws javax.naming.NamingException
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public UserSessionController() throws NamingException, SQLException, ClassNotFoundException {
        super();

      //todo Тут конечно раскрыть потом все это надо будет
        //  messages = new MessageSystem();

       // ejbUserSession = EJBUtils.getEJBAgentSession(getResobj_RemoteAppServerInitialContext());

       // userManager = new UserManager();

        //-- Define user type
        //userManager.setUserType(this.getAppParams().getDefaultUserType());

        //-- WL set UserType
        //-- когда откажемся от ejb BL нужно удалить
//        ejbUserSession.setUserType(this.getAppParams().getDefaultUserType());
    }


    @PostConstruct
    @Override
    protected void postConstruct() {
        super.postConstruct();
        action = Consts.actions.logout;
    }


    /**
     * Текущий пользователь
     *
     * @return
     */
    public User getUser() {
        return userManager.getUser();
    }

    /**
     * Выставляем текущее действие, которое должна выполнить система
     *
     * @param action
     */
    public void setSimpleAction(Consts.actions action) {
        wpanchor = "#";
        errorMessage = "";

        try {
//            if (ResoUtils.isEmpty(getEjbUserSession())) {
//                throw new NoSuchEJBException();
//            }

//            if (action != Consts.actions.stop) {
//                getEjbUserSession().getUser();
//            }
            //-- Успешная авторизация
            //todo вот это пока скроем. Но потом обязательно надо восстановить.
            //if (getUser().isAuthorized()) {
                this.action = action;
            //} else {
            //    errorMessage = Notes.noteErrorSessionNotAuthorized;
            //    this.action = Consts.actions.logout;
           // }
        } catch (NoSuchEJBException e) {
            errorMessage = Notes.noteErrorSessionNotAuthorized;
            //--
            logOut();

            throw new NoSuchEJBException();
        }
    }

    /**
     * Авторизация. Отключение пользователя от "Online - Рабочее место".
     *
     * @return
     */
    public String logOut() {
        errorMessage = "";

        if (FacesContext.getCurrentInstance() != null) {
            //if (getHttpSession() != null) {
                /*todo уберем пока это. Потом восстановим
                 * Инвалидация вызовет убийство сессионных jsf бинов, поэтому будут выполнены проц-ры deinit
                 */
              //  getHttpSession().invalidate();
                action = Consts.actions.logout;
        //    }
        }

        return action.toString();
    }


}
