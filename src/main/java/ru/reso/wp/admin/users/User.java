package ru.reso.wp.admin.users;


import ru.reso.wp.admin.users.employee.UserEmployeeProfile;

import javax.inject.Inject;

/**
 * Класс юзера. Видимо того, который зашел и имеет сейчас сессию на wp.reso.ru. Мне этот пекедж вообще не переписали. Что там в этом классе остается только догадываться.
 * Так что делаем "болванку".
 *
 * @author Anton Romanov [ROMAB] 07.05.2018 12:39
 */

public class User {

    @Inject
    private UserEmployeeProfile profile;


    public UserEmployeeProfile getProfile() {
        return profile;
    }



}
