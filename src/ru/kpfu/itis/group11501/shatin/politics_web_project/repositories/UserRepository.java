package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Message;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import java.time.LocalDate;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface UserRepository {
    User getUserByEmail(String email);

    boolean userExists(String email, String password);

    boolean containsThisEmail(String email);

    boolean samePassportExists(String passportSeries, String passportNum);

    boolean addNewUser(String password, String email, Role role, int timezoneOffset, String passportSeries, String passportNum, String name, String surname, String patronymic, LocalDate birthdayDate);

    User getUserById(long userId);

    boolean updateEmail(long userId, String email);

    boolean updatePassword(long userId, String hashedPassword);
}
