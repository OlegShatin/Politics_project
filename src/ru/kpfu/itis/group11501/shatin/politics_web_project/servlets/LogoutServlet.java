package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.CookieMaster;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls.CookieMasterImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Oleg Shatin
 *         11-501
 */
@WebServlet(name = "LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private CookieMaster cookieMaster;

    @Override
    public void init() throws ServletException {
        super.init();
        cookieMaster = new CookieMasterImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        request.getSession().removeAttribute("user");
        cookieMaster.removeCookies(request,response);
        response.sendRedirect("/login?last_email=" + user.getEmail());
    }
}
