package ru.kpfu.itis.group11501.shatin.politics_web_project.services;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import java.time.LocalDate;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface UserService {
    boolean userExists(String email, String hashed_password);

    User getUser(String email);


    boolean emailAlreadyExists(String email);

    boolean samePassportExists(String passportSeries, String passportNum);


    boolean passportDataIsValid(String passportSeries, String passportNum);

    User createNewUser(String password, String email, Role role, int timezoneOffset, String passportSeries, String passportNum, String name, String surname, String patronymic, LocalDate birthdayDate);

    User getUser(long userId);
}
