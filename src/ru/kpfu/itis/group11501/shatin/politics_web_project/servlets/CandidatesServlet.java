package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.*;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.CandidateService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.ElectionService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.CandidateServiceImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.ElectionServiceImpl;
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
@WebServlet(name = "CandidatesServlet")
public class CandidatesServlet extends HttpServlet {
    private ElectionService electionService;
    private UserService userService;
    private CandidateService candidateService;

    @Override
    public void init() throws ServletException {
        super.init();
        electionService = new ElectionServiceImpl();
        userService = new UserServiceImpl();
        candidateService = new CandidateServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = userService.authorize(request.getSession().getAttribute("user"));
        Election election = electionService.getNextElectionForUser(user);
        if (election != null && user != null && user.getRole() != Role.GUEST && request.getParameter("candidate_id") != null) {
            if (request.getParameter("message_text") != null) {
                try {
                    Long candidateId = Long.parseLong(request.getParameter("candidate_id"));
                    Message newMessage = userService.addMessage(
                            user,
                            candidateService.getCandidateFromElectionById(election, candidateId).getAgentId(),
                            request.getParameter("message_text")
                    );
                    if (newMessage != null) {
                        response.sendRedirect("/candidates?success_message_to=" + request.getParameter("candidate_id"));
                    } else
                        response.sendRedirect("/candidates?error=message_was_not_sent");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("/404");
                }
            } else {
                response.sendRedirect("/candidates?error=empty_message");
            }
        } else
            response.sendRedirect("/404");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = userService.authorize(request.getSession().getAttribute("user"));
        HashMap<String, Object> root = new HashMap<>();
        root.put("user_role", user.getRole());
        root.put("error", request.getParameter("error"));
        Election election = electionService.getNextElectionForUser(user);
        if (election != null) {
            root.put("election", election);
            if (request.getParameter("success_message_to") != null) {
                Long candidateId = null;
                try {
                    candidateId = Long.parseLong(request.getParameter("success_message_to"));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    response.sendRedirect("/404");
                }
                root.put("success_message_candidate", candidateService.getCandidateFromElectionById(election, candidateId));
            }
            root.put("candidates", election.getCandidates());
            Helper.render(request, response, "candidates.ftl", root);
        } else {
            Helper.render(request, response, "no_elections.ftl", root);
        }
    }
}
