package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Comment;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.NewsService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.NewsServiceImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

/**
 * @author Oleg Shatin
 *         11-501
 */
@WebServlet(name = "ArticleServlet")
public class ArticleServlet extends HttpServlet {
    private UserService userService;
    private NewsService newsService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserServiceImpl();
        newsService = new NewsServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //// TODO: 05.11.2016 Add datetime after DB
        Comment newComment = new Comment(Long.parseLong(request.getParameter("parent_comment_id")),
                Long.parseLong(request.getParameter("a")), ((User)request.getSession().getAttribute("user")).getID(),
                        request.getParameter("comment_text"),
               OffsetDateTime.now(((User)request.getSession().getAttribute("user")).getTimezoneOffset()));
        newComment = newsService.addComment(newComment);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User)request.getSession().getAttribute("user");
        HashMap<String, Object> root = new HashMap<>();
        if (currentUser == null){
            root.put("user_role", Role.GUEST);
        } else {
            root.put("user_role", currentUser.getRole());
        }
        root.put("article", newsService.getArticle(Long.parseLong(request.getParameter("a"))));
        Helper.render(request, response, "login.ftl", root);
    }
}
