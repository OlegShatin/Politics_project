package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import org.json.JSONException;
import org.json.JSONObject;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.NewsService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.NewsServiceImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.UserServiceImpl;

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
@WebServlet(name = "EmailCheckAjaxServlet")
public class EmailCheckAjaxServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        if (email != null){
            try {
                JSONObject jsonObject = new JSONObject();
                if (userService.emailAlreadyExists(email)) {
                    jsonObject.put("result", false);
                } else {
                    jsonObject.put("result", true);
                }
                response.setContentType("text/json");
                response.getWriter().println(jsonObject.toString());
            }  catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
