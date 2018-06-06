package ru.reso.wp.srv;


import ru.reso.wp.srv.consts.ResoSrvConsts;

/**
 * Судя по всему какой-то серверный класс. Что он делает, пока не понятно. Мне этот класс вообще не дали. Поэтому делаем просто "болванку",
 * а там потом разберемся чо куда...
 *
 * @rewrite Anton Romanov [ROMAB] 07.05.2018 12:28
 */

public class ResoRemoteObject {
    public ResoRemoteObject(ResoSrvConsts.TAppWorkMode aWorkMode, String aPrimaryDBName) {
    }

    public ResoRemoteObject() {

    }


    public Resobj_EjbDatabaseInteraction getResobj_EjbDatabaseInteraction() {

        Resobj_EjbDatabaseInteraction rem = new Resobj_EjbDatabaseInteraction();

        return rem;
    }
}
