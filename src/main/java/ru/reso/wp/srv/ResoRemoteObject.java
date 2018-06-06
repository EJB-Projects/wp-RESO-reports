/*
 * Document   : Main parent class for remote invoke DB
 * Content    :
 * Created on : 24.10.2016
 * Author     : kajam
 * Description: Главный класс для удаленной работы с БД
 *              От него должны насдледоваться все остальные классы в приложениях РЕСО
 */

package ru.reso.wp.srv;


import ru.reso.wp.srv.consts.ResoSrvConsts;

import javax.annotation.PostConstruct;

/**
 * Судя по всему какой-то серверный класс. Что он делает, пока не понятно. Мне этот класс вообще не дали. Поэтому делаем просто "болванку",
 * а там потом разберемся чо куда...
 *
 * @rewrite Anton Romanov [ROMAB] 07.05.2018 12:28
 */

public class ResoRemoteObject extends ResoObject {
    public ResoRemoteObject(ResoSrvConsts.TAppWorkMode aWorkMode, String aPrimaryDBName) {
    }

    public ResoRemoteObject() {

    }


    public Resobj_EjbDatabaseInteraction getResobj_EjbDatabaseInteraction() {

        Resobj_EjbDatabaseInteraction rem = new Resobj_EjbDatabaseInteraction();

        return rem;
    }

    @PostConstruct
    @Override
    protected void postConstruct() {
        //--
        super.postConstruct();

        //-- init config data from database
      //  ConfigLoader.fillDataBaseConfig(resobj_EjbDatabaseInteraction);
    }


}
