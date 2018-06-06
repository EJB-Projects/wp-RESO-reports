/*
 * Document   : Main DB parent class
 * Content    :
 * Created on : 27.05.2015, 14:51:57
 * Author     : kajam
 * Description: Главный класс для работы с БД от которого должны насдледоваться
 *              все остальные классы в приложениях РЕСО
 */

package ru.reso.wp.srv;


import javax.annotation.PostConstruct;

/**
 *
 * @author kajam
 */

public class ResoObject {


    /**
     * Режим работы приложения
     *
     * По умолчанию берется из конфигурационного файла (reso-config.xml), но в
     * приложении может переопределяться пользователем Если пользователь выбрал
     * тестовый режим (TEST), то в качестве БД используется та, которую указал
     * пользователь Для настроек используется файл web.xml веб-приложения.
     *
     * Параметры:
     * <param-name>resoWorkMode</param-name>
     * <param-name>primaryDBName</param-name>
     *
     */

    @PostConstruct
    protected void postConstruct() {
        //--
    }


}
