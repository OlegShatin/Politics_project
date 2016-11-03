package ru.kpfu.itis.group11501.shatin.politics_web_project.services;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class UserServiceImpl implements UserService {
    @Override
    public boolean userExists(String email, String password) {
        return false;
    }

    @Override
    public User getUser(String email) {
        return null;
    }
}
