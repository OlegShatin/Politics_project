package ru.kpfu.itis.group11501.shatin.politics_web_project.services;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.UserRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.UserRepositoryImpl;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    public UserServiceImpl(){
        userRepository = new UserRepositoryImpl();
    }
    @Override
    public boolean userExists(String email, String password) {
        return false;
    }

    @Override
    public User getUser(String email) {
        return userRepository.getUserByEmail(email);
    }
}
