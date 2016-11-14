package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import javax.servlet.http.Cookie;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface CookieRepository {
    Cookie addCookie(User currentUser, String cookieName);

    long getUserId(String encryptedValue);

    Cookie updateCookie(User user, String cookieName);
}
