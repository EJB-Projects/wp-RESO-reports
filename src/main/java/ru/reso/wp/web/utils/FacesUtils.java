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
     *
     *
     * [ROMAB] 18.05.2018 12:44 ТО ЕСТЬ Тут мы по идее возвращаем бин по имени
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

    /**
     *
     * [ROMAB] 18.05.2018 12:30. Докопал досюда. Откуда пришел: когда щелкаешь в дереве отчетов на отчете, после небольших логических изысканий дергается вот такая шняга:
     *
     *
     * this.getUserSessionController().setSimpleAction(Consts.actions.viewReportForm);
     *
     * ... которая приводит как раз сюда. Чтобы до конца врубиться что это, надо почитать книжку по Java Server Faces и хорошенько разобраться в теме. Я бегло погуглил, толком ничего не нашел.
     * Но как я плюс/минус километр примерно понял getAppliction возвращает нам инстанс всего нашего приложения. То есть приложение судя по всему в данном случае - это вот мы на серваке развернули
     * war, он там задеплоил jsf-ки которую несут какую-то логику и вот все вместе это несет в себе некий а-ля как в спринге контекст приложения. И мы берем его инстанс сначала.
     *
     * getValueBinding по идее типа должен возвращать какой-то объект связывающий контекст и бин. Ну по структуре методов он возвращает просто бин по имени. Я очень надеюсь, что и для Icefaces и для
     * PrimeFaces JSF един. Типа это же основа, на которую накручиваются эти фреймворки. API должно быть одно.
     *
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
