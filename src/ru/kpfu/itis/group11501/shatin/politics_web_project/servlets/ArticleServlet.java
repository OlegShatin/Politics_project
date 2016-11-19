package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Article;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Comment;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.NewsService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.NewsServiceImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.UserServiceImpl;

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
@WebServlet(name = "ArticleServlet")
@Deprecated
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

        User currentUser = (User)request.getSession().getAttribute("user");
        if (currentUser != null) {
            Comment newComment = new Comment(Long.parseLong(request.getParameter("parent_comment_id")),
                    Long.parseLong(request.getParameter("a")), currentUser.getID(),
                    request.getParameter("comment_text"),
                    OffsetDateTime.now(currentUser.getTimezoneOffset()));
            newComment = newsService.addComment(newComment);
        }
        response.sendRedirect(request.getServletPath());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User)request.getSession().getAttribute("user");
        HashMap<String, Object> root = new HashMap<>();
        if (currentUser == null){
            root.put("user_role", Role.GUEST);
        } else {
            root.put("user_role", currentUser.getRole());
        }
        Article article = newsService.getArticle(Long.parseLong(request.getParameter("a")), //// TODO: 09.11.2016 fix this hardcode: required tome offset session atribute
                currentUser != null ? currentUser : new User(Role.GUEST, OffsetDateTime.now().getOffset()));
        root.put("article", article);
        root.put("comments", newsService.getCommentsOfArticleForUser(article,//// TODO: 09.11.2016 fix this hardcode: required tome offset session atribute 
                currentUser != null ? currentUser : new User(Role.GUEST, OffsetDateTime.now().getOffset())));
        Helper.render(request, response, "article.ftl", root);
    }
}
