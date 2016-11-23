package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Container;
import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ContainerOfUsers;
import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.*;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.CandidateService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.ElectionService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.NewsService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.CandidateServiceImpl;
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


    private UserService userService;
    private ElectionService electionService;
    private CandidateService candidateService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userService = new UserServiceImpl();
        this.electionService = new ElectionServiceImpl();
        this.candidateService = new CandidateServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Election election = electionService.getNextElectionForUser(user);
        if (election != null && user != null && user.getRole() != Role.GUEST && request.getParameter("id") != null) {
            if (request.getParameter("message_text") != null) {
                if (user.getRole() == Role.USER) {
                    try {
                        Long candidateId = Long.parseLong(request.getParameter("id"));
                        Message newMessage = userService.addMessage(
                                user,
                                candidateService.getCandidateFromElectionById(election, candidateId).getAgentId(),
                                request.getParameter("message_text")
                        );
                        if (newMessage != null) {
                            response.sendRedirect("/conversations?id=" + request.getParameter("id"));
                        } else
                            response.sendRedirect("/conversations?error=message_was_not_sent");
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.sendRedirect("/404");
                    }
                    //if user agent or admin
                } else {
                    //// TODO: 23.11.2016 remove or approve this
                    response.sendRedirect("/404");
                }
            } else {
                response.sendRedirect("/candidates?error=empty_message");
            }
        } else
            response.sendRedirect("/404");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


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
                        p = Integer.parseInt(request.getParameter("p"));
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
                    for (Map.Entry<Message, Candidate> entry : sortedResult.entrySet()) {
                        listOfContainers.add(new Container(entry.getKey(), entry.getValue()));
                    }
                    root.put("conversations", listOfContainers);
                } else {
                    if (user.getRole() == Role.ADMIN || user.getRole() == Role.AGENT) {
                        SortedMap<Message, User> sortedResult = userService.getPageOfConversationsWithUsersForUser(user, p);
                        ArrayList<ContainerOfUsers> listOfContainers = new ArrayList<>();
                        for (Map.Entry<Message, User> entry : sortedResult.entrySet()) {
                            listOfContainers.add(new ContainerOfUsers(entry.getKey(), entry.getValue()));
                        }
                        root.put("conversations", listOfContainers);
                    }
                }
                root.put("user", user);
                Helper.render(request, response, "conversations.ftl", root);
            } else {
                //id - another user or candidate in conversation
                Long selectedId = null;
                try {
                    selectedId = Long.parseLong(request.getParameter("id"));
                } catch (NumberFormatException e) {
                    response.sendRedirect("/404");
                }

                List<Message> sortedResult = null;
                //check who is user
                if (user.getRole() == Role.USER) {
                    //if just user: need candidate like opponent
                    Candidate candidate = candidateService.getCandidateById(selectedId);
                    if (candidate != null) {
                        sortedResult = userService.getConversationWithUserForUser(user, candidate.getAgentId());
                        root.put("opponent", candidate);
                    } else {
                        response.sendRedirect("/404");
                    }
                } else {
                    //if admin or agent: need user like opponent
                    User otherUser = userService.getUser(selectedId);
                    if (otherUser != null) {
                        sortedResult = userService.getConversationWithUserForUser(user, otherUser.getId());
                        root.put("opponent", otherUser);
                    } else {
                        response.sendRedirect("/404");
                    }
                }

                if (sortedResult != null) {
                    root.put("messages", sortedResult);
                } else
                    response.sendRedirect("/404");
                root.put("user", user);
                Helper.render(request, response, "conversation.ftl", root);
            }
        } else
            response.sendRedirect("/login");
    }


}
