package ru.kpfu.itis.group11501.shatin.politics_web_project.helpers;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserServiceImpl;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Oleg Shatin
 *         11-501
 */
public class CookieMaster {

    public static void addRememberCookie(User currentUser, HttpServletRequest request, HttpServletResponse response) {
        Cookie loginCookie = null;
        String key = Helper.getHashedString(currentUser.getEmail() + currentUser.getHashedPassword());
        Cookie keyCookie = new Cookie("key", key);
        try {
            loginCookie = new Cookie("user", AES.encrypt(currentUser.getEmail(), key));
        } catch (Exception e) {
            e.printStackTrace();
            loginCookie = new Cookie("user",currentUser.getEmail());
        }
        //Cookie loginCookie = new Cookie("user",currentUser.getEmail());
        keyCookie.setMaxAge(60 * 60 * 24 * 365 * 10);
        response.addCookie(keyCookie);
        loginCookie.setMaxAge(60 * 60 * 24 * 365 * 10);
        response.addCookie(loginCookie);
    }

    public static User getUser(HttpServletRequest request) {
        UserService userService = new UserServiceImpl();
        Cookie[] cookies = request.getCookies();
        User currentUser = null;
        String encryptedEmail = null;
        String key = null;
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("user"))
                encryptedEmail = cookie.getValue();
            if (cookie.getName().equals("key"))
                key = cookie.getValue();
        }
        try {
            currentUser = userService.getUser(AES.decrypt(encryptedEmail,key));
        } catch (Exception e) {
            currentUser = userService.getUser(encryptedEmail);
            e.printStackTrace();
        }
        return currentUser;
    }
}
