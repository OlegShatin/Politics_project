package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.NewsService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.NewsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.HashMap;

/**
 * @author Oleg Shatin
 *         11-501
 */
@WebServlet(name = "NewsServlet")
public class NewsServlet extends HttpServlet {

    private NewsService newsService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.newsService = new NewsServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User)request.getSession().getAttribute("user");
        HashMap<String, Object> root = new HashMap<>();
        if (currentUser == null){
            root.put("user_role", Role.GUEST);
        } else {
            root.put("user_role", currentUser.getRole());
        }
        root.put("articles", newsService.getArticlesForUser(currentUser != null ? currentUser : new User(Role.GUEST, OffsetDateTime.now().getOffset())));
        //// TODO: 10.11.2016 add clocks info 
        Helper.render(request, response, "news.ftl", root);
    }
}
