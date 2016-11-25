package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Election;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.ElectionResult;
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
import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
@WebServlet(name = "StatisticServlet")
public class StatisticServlet extends HttpServlet {
    private UserService userService;
    private ElectionService electionService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserServiceImpl();
        electionService = new ElectionServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = userService.authorize(request.getSession().getAttribute("user"));
        HashMap<String, Object> root = new HashMap<>();
        root.put("user_role", user.getRole());
        root.put("error", request.getParameter("error"));
        List<Election> finishedElections = null;
        if (request.getParameter("el") == null) {
            //list of finished elections
            String filter = request.getParameter("filter");
            if (filter != null) {
                if (filter.equals("parl")) {
                    finishedElections = electionService.getFinishedParliamentRawElections();
                    root.put("filter",filter);
                } else {
                    if (filter.equals("pres")) {
                        finishedElections = electionService.getFinishedPresidentRawElections();
                        root.put("filter",filter);
                    } else {
                        if (filter.equals("all")) {
                            root.put("filter", filter);
                            finishedElections = electionService.getFinishedAllRawElections();
                        }
                    }
                }
            } else {
                finishedElections = electionService.getFinishedAllRawElections();
            }
            if (finishedElections != null){
                root.put("elections",finishedElections);
            }
            Helper.render(request,response,"elections.ftl", root);
        } else {
            try {
                Long electionId = Long.parseLong(request.getParameter("el"));
                ElectionResult result = electionService.getElectionResultFromFinished(electionId);
                if (result != null){
                    root.put("result", result);
                    Helper.render(request,response,"result.ftl",root);
                } else {
                    response.sendRedirect("/404");
                }
            } catch (NumberFormatException e){
                e.printStackTrace();
                response.sendRedirect("/404");
            }
        }
    }
}
