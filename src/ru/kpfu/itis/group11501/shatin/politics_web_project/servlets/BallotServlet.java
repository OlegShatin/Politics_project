package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Election;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.ElectionService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.ElectionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Oleg Shatin
 *         11-501
 */
@WebServlet(name = "BallotServlet")
public class BallotServlet extends HttpServlet {
    private ElectionService electionService;

    @Override
    public void init() throws ServletException {
        super.init();
        electionService = new ElectionServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Election election = electionService.getCurrentElectionForUser(user);
        if (user.getRole() == Role.USER && !electionService.userVotedOnElection(user, election)) {
            Long votedCandidateId = null;
            try {
                votedCandidateId = Long.parseLong(request.getParameter("voted_candidate"));
                if (electionService.addVoteForCandidate(user, election, votedCandidateId)) {
                    response.sendRedirect("/news?alert=thanks");
                } else {
                    response.sendRedirect("/ballot?error=your_vote_was_not_accepted");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("/ballot?error=incorrect_input");
            }
        } else {
            response.sendRedirect("/news?error=access_denied");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Election election = electionService.getCurrentElectionForUser(user);
        if (user.getRole() == Role.USER && !electionService.userVotedOnElection(user, election)) {

            HashMap<String, Object> root = new HashMap<>();
            root.put("user_role", user.getRole());
            root.put("candidates", election.getCandidates());
            root.put("end_time", election.getFinishTime());
            root.put("election_type", election.getType());
            root.put("election_finish", election.getFinishTime());
            root.put("error", request.getParameter("error"));
            Helper.render(request, response, "ballot.ftl", root);
        } else {
            response.sendRedirect("/news?error=access_denied");
        }
    }
}
