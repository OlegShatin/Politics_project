package ru.kpfu.itis.group11501.shatin.politics_web_project.helpers;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface CookieMaster {
    public void addRememberCookie(User currentUser, HttpServletResponse response);
    public User getUser(HttpServletRequest request);
    public void removeCookies(HttpServletRequest request, HttpServletResponse response);

    void updateCookies(User currentUser, HttpServletResponse response);
}
