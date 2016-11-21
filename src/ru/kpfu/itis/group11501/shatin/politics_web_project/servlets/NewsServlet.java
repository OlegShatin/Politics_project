package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.*;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.*;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.ElectionServiceImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.NewsServiceImpl;
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
@WebServlet(name = "NewsServlet")
public class NewsServlet extends HttpServlet {

    private NewsService newsService;
    private UserService userService;
    private ElectionService electionService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.newsService = new NewsServiceImpl();
        this.userService = new UserServiceImpl();
        this.electionService = new ElectionServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User)request.getSession().getAttribute("user");
        if (currentUser != null && (currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.USER)) {
            Long parentCommentId = null;
            if (request.getParameter("parent_comment_id") != null) {
                try {
                    parentCommentId = Long.parseLong(request.getParameter("parent_comment_id"));
                } catch (NumberFormatException e){
                    e.printStackTrace();
                    response.sendRedirect("/404");
                }
            }
            Comment newComment = new Comment(parentCommentId ,
                    Long.parseLong(request.getParameter("a")), currentUser.getID(),
                    request.getParameter("comment_text"),
                    OffsetDateTime.now(currentUser.getTimezoneOffset()));
            newComment = newsService.addComment(newComment);
            if (newComment != null) {
                response.sendRedirect("/news" + "?a=" + request.getParameter("a"));
            } else {
                response.sendRedirect("/news"+"?a=" + request.getParameter("a") +"&error=smth_wrong");
            }
        } else {
            response.sendRedirect("/news"+"?a=" + request.getParameter("a") +"&error=access_denied");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //get user from session or create Guest user
        User user = (User) request.getSession().getAttribute("user") != null ?
                (User) request.getSession().getAttribute("user") : userService.getGuest();
        HashMap<String, Object> root = new HashMap<>();
        root.put("user_role", user.getRole());
        root.put("error", request.getParameter("error"));
        //// TODO: 10.11.2016 add clocks info
        //if required all news send news list, if required article - send article
        if (request.getParameter("a") == null) {
            root.put("articles", newsService.getArticlesForUser(user));
            Election election = electionService.getCurrentElectionForUser(user);
            root.put("user_cannot_vote", (user.getRole() != Role.USER || election == null || electionService.userVotedOnElection(user, election)));
            Helper.render(request, response, "news.ftl", root);
        } else {
            long articleNum = 0;
            try {
                articleNum = Long.parseLong(request.getParameter("a"));
            } catch (NumberFormatException e){
                response.sendRedirect("/404");
            }
            Article article = newsService.getArticle(articleNum , user);
            root.put("article", article);
            root.put("comments", newsService.getCommentsOfArticleForUser(article, user));
            Helper.render(request, response, "article.ftl", root);
        }
    }
}
