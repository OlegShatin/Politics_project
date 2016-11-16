package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Article;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.CommentNode;
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
import java.util.HashMap;

/**
 * @author Oleg Shatin
 *         11-501
 */
@WebServlet(name = "NewsServlet")
public class NewsServlet extends HttpServlet {

    private NewsService newsService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.newsService = new NewsServiceImpl();
        this.userService = new UserServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get user from session or create Guest user
        User currentUser = (User) request.getSession().getAttribute("user") != null ?
                (User) request.getSession().getAttribute("user") : userService.getGuest();
        HashMap<String, Object> root = new HashMap<>();
        root.put("user_role", currentUser.getRole());
        root.put("articles", newsService.getArticlesForUser(currentUser));
        //// TODO: 10.11.2016 add clocks info
        //if required all news send news list, if required article - send article
        if (request.getParameter("a") == null) {
            Helper.render(request, response, "news.ftl", root);
        } else {
            long articleNum = 0;
            try {
                articleNum = Long.parseLong(request.getParameter("a"));
            } catch (NumberFormatException e){
                response.sendRedirect("/404");
            }
            Article article = newsService.getArticle(articleNum , currentUser);
            root.put("article", article);
            for (CommentNode commentNode: newsService.getCommentsOfArticleForUser(article, currentUser)){
                System.out.println(commentNode.getComment().getText());
            }
            root.put("comments", newsService.getCommentsOfArticleForUser(article, currentUser));
            Helper.render(request, response, "article.ftl", root);
        }
    }
}
