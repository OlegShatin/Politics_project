package ru.kpfu.itis.group11501.shatin.politics_web_project.helpers;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.CookieRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.CookieRepositoryImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserServiceImpl;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Oleg Shatin
 *         11-501
 */
public class CookieMasterImpl implements CookieMaster {

    private CookieRepository cookieRepository;
    private UserService userService;
    public CookieMasterImpl(){
        cookieRepository = new CookieRepositoryImpl();
        userService = new UserServiceImpl();
    }
    @Override
    public void addRememberCookie(User currentUser, HttpServletResponse response) {

        Cookie loginCookie = cookieRepository.addCookie(currentUser, "user");
        loginCookie.setMaxAge(60 * 60 * 24 * 365 * 10);
        response.addCookie(loginCookie);
    }
    @Override
    public User getUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        User currentUser = null;
        String encryptedValue = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user"))
                    encryptedValue = cookie.getValue();
            }
            if (encryptedValue != null) {
                try {
                    currentUser = userService.getUser(cookieRepository.getUserId(encryptedValue));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return currentUser;
    }
    @Override
    public void removeCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    @Override
    public void updateCookies(User currentUser, HttpServletResponse response) {
        Cookie loginCookie = cookieRepository.updateCookie(currentUser, "user");
        loginCookie.setMaxAge(60 * 60 * 24 * 365 * 10);
        response.addCookie(loginCookie);
    }
}
