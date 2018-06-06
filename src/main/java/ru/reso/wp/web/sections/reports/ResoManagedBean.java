/*
 * Document   : Main parent class
 * Content    : Управляемый бин
 * Created on : 27.05.2015, 14:51:57
 * Author     : kajam
 * Description: Главный класс от которого должны насдледоваться все остальные
 *              классы в приложениях РЕСО
 */
package ru.reso.wp.web.sections.reports;

import ru.reso.wp.srv.ResoRemoteObject;
import ru.reso.wp.web.administrator.controller.UserSessionController;
import ru.reso.wp.web.utils.ManagedBeanUtils;

import javax.annotation.PostConstruct;

public class ResoManagedBean  extends ResoRemoteObject {


    /**
     * Managed bean. Сессия пользователя.
     *
     * @return
     */
    public UserSessionController getUserSessionController() {
        return ManagedBeanUtils.getUserSessionController();
    }

    @PostConstruct
    @Override
    protected void postConstruct() {
        super.postConstruct();
    }

}
