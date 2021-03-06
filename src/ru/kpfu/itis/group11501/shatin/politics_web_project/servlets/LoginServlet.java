package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.services.CookieMaster;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.CookieMasterImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Oleg Shatin
 *         11-501
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {

    private UserService usersService;
    private CookieMaster cookieMaster;

    @Override
    public void init() throws ServletException {
        super.init();
        usersService = new UserServiceImpl();
        cookieMaster = new CookieMasterImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (usersService.emailIsValid(email)) {
            if (usersService.userExists(email, password)) {
                User currentUser = usersService.getUser(email);
                request.getSession().setAttribute("user", currentUser);
                if (request.getParameter("remember_request") != null) {
                    cookieMaster.addRememberCookie(currentUser, response);
                }
                response.sendRedirect("/news");
            } else {
                response.sendRedirect("/login?error=UserDoesNotExist");
            }
        } else
            response.sendRedirect("/login?error=InvalidEmail");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> root = new HashMap<>();
        root.put("last_email", request.getParameter("last_email"));
        root.put("error", request.getParameter("error"));
        Helper.render(request, response, "login.ftl", root);
    }
}
