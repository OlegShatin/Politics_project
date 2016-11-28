package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;


import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.AdminService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.AdminServiceSingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleg Shatin
 *         11-501
 */
@WebServlet(name = "AdminServlet")
@MultipartConfig
public class AdminServlet extends HttpServlet {
    private UserService userService;
    private AdminService adminService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserServiceImpl();
        adminService = AdminServiceSingleton.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = userService.authorize(request.getSession().getAttribute("user"));
        if (user != null && user.getRole() == Role.ADMIN && request.getParameter("table") != null) {
            String method = request.getParameter("method");
            String table = request.getParameter("table");
            String field = request.getParameter("field");
            String value = request.getParameter("value");
            String filter = request.getParameter("filter");
            Map<String, String[]> data = request.getParameterMap();
            Map<String,Object> result = adminService.doQuery(method, table, field, value, filter, data);
            if (result.get("error") != null){
                response.sendRedirect("/admin?table=" + table + "&error=" + result.get("error"));
            }
        } else {
            response.sendRedirect("/404");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = userService.authorize(request.getSession().getAttribute("user"));
        HashMap<String, Object> root = new HashMap<>();
        root.put("pass_upd", request.getParameter("pass_upd"));
        root.put("email_upd", request.getParameter("email_upd"));
        root.put("user_role", user.getRole());
        root.put("error", request.getParameter("error"));

    }
}
