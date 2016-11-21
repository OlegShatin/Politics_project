package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.CandidateService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.CandidateServiceImpl;

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
@WebServlet(name = "ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private CandidateService candidateService;

    @Override
    public void init() throws ServletException {
        super.init();
        candidateService = new CandidateServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //// TODO: 21.11.2016 implement this
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        HashMap<String, Object> root = new HashMap<>();
        root.put("user_role", user.getRole());
        root.put("error", request.getParameter("error"));
        if (user.getRole() == Role.USER) {
            root.put("user", user);
            Helper.render(request, response, "profile.ftl", root);
        } else {
            if (user.getRole() == Role.AGENT) {
                Candidate candidate = candidateService.getCandidateForAgent(user);
                if (candidate != null) {
                    root.put("candidate", candidate);
                } else {
                    response.sendRedirect("/404");
                }
            } else {
                response.sendRedirect("/news?error=access_denied");
            }
        }
    }
}
