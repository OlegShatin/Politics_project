package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Election;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.ElectionService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.ElectionServiceImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.UserServiceImpl;

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
@WebServlet(name = "CandidatesServlet")
public class CandidatesServlet extends HttpServlet {
    private ElectionService electionService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        electionService = new ElectionServiceImpl();
        userService = new UserServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user") != null ?
                (User) request.getSession().getAttribute("user") : userService.getGuest();
        HashMap<String, Object> root = new HashMap<>();
        root.put("user_role", user.getRole());
        root.put("error", request.getParameter("error"));
        Election election = electionService.getNextElectionForUser(user);
        if (election != null) {
            root.put("election", election);
            root.put("candidates", election.getCandidates());
            Helper.render(request, response, "candidates.ftl", root);
        } else {
            Helper.render(request, response, "no_elections.ftl", root);
        }
    }
}
