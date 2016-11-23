package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Container;
import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ContainerOfUsers;
import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.*;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.ElectionService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.NewsService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserService;
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
import java.util.*;

/**
 * @author Oleg Shatin
 *         11-501
 */
@WebServlet(name = "ConversationsServlet")
public class ConversationsServlet extends HttpServlet {

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
        if (currentUser != null && (currentUser.getRole() != Role.GUEST)) {
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
                    Long.parseLong(request.getParameter("a")), currentUser.getId(),
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

        //// TODO: 23.11.2016 implement for Agent
        User user = (User) request.getSession().getAttribute("user") != null ?
                (User) request.getSession().getAttribute("user") : userService.getGuest();
        if (user != null && user.getRole() != Role.GUEST) {
            HashMap<String, Object> root = new HashMap<>();
            root.put("user_role", user.getRole());
            root.put("error", request.getParameter("error"));
            if (request.getParameter("id") == null) {
                //pagination
                int p = 1;
                if (request.getParameter("p") != null) {
                    try {
                        p =Integer.parseInt(request.getParameter("p"));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        p = 1;
                    }
                }
                int maxPage = userService.getMaxPageOfListOfConversationsForUser(user);
                if (p > maxPage)
                    response.sendRedirect("/404");
                root.put("page", p);
                root.put("max_page", maxPage);

                //list of conversations
                if (user.getRole() == Role.USER) {
                    Election election = electionService.getNextElectionForUser(user);
                    if (election != null) {
                        root.put("unmessaged_candidates", userService.getUnmessagedCandidatesByUser(user, election.getCandidates()));
                    }
                    //solution to implement sorted map in templates
                    SortedMap<Message, Candidate> sortedResult = userService.getPageOfConversationsWithCandidatesForUser(user, p);
                    ArrayList<Container> listOfContainers = new ArrayList<>();
                    for (Map.Entry<Message, Candidate> entry : sortedResult.entrySet()){
                        listOfContainers.add(new Container(entry.getKey(), entry.getValue()));
                    }
                    root.put("conversations", listOfContainers);
                } else {
                    if (user.getRole() == Role.ADMIN ||user.getRole()== Role.AGENT){
                        SortedMap<Message, User> sortedResult = userService.getPageOfConversationsWithUsersForUser(user, p);
                        ArrayList<ContainerOfUsers> listOfContainers = new ArrayList<>();
                        for (Map.Entry<Message, User> entry : sortedResult.entrySet()){
                            listOfContainers.add(new ContainerOfUsers(entry.getKey(), entry.getValue()));
                        }
                        root.put("conversations", listOfContainers);
                    }
                }
                root.put("user",user);
                Helper.render(request, response, "conversations.ftl", root);
            } else {
               /* //id - another user in conversation
                long articleNum = 0;
                try {
                    articleNum = Long.parseLong(request.getParameter("a"));
                } catch (NumberFormatException e) {
                    response.sendRedirect("/404");
                }
                Article article = newsService.getArticle(articleNum, user);
                root.put("article", article);
                root.put("comments", newsService.getCommentsOfArticleForUser(article, user));
                Helper.render(request, response, "article.ftl", root);*/
                response.sendRedirect("/");
            }
        } else
            response.sendRedirect("/login");
    }
}
