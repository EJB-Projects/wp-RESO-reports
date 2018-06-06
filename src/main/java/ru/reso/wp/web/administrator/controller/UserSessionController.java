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
import ru.reso.wp.web.sections.reports.ResoManagedBean;
import ru.reso.wp.admin.manager.UserManager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

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
     * Текущий пользователь
     *
     * @return
     */
    public User getUser() {
        return userManager.getUser();
    }


}
