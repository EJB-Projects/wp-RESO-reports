/*
 * Document   : ManagedBeanUtils
 * Content    :
 * Created on : 01.09.2014, 14:51:57
 * Author     : Kajam
 * Description:
 */

package ru.reso.wp.web.utils;

import ru.reso.wp.web.administrator.controller.UserSessionController;
import ru.reso.wp.web.consts.ManagedBeanConsts;

public class ManagedBeanUtils {


    /**
     * Managed Bean Application Scoped. Кеш данных.
     *
     * @return
     */


    /**
     * Managed Bean. Сессия пользователя.
     *
     * @return
     */
    public static UserSessionController getUserSessionController() {
        return (UserSessionController) FacesUtils.getManagedBean(ManagedBeanConsts.USER_SESSION_CONTROLLER);
    }



}
