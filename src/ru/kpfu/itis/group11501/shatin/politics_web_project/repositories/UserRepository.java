package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface UserRepository {
    User getUserByEmail(String email);

    boolean userExists(String email, String password);
}
