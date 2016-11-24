package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

/**
 * @author Oleg Shatin
 *         11-501
 */
@WebServlet(name = "SignupServlet")
public class SignupServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("password") == null || request.getParameter("repeated_password") == null
                || request.getParameter("birthday_date") == null || request.getParameter("email") == null
                || request.getParameter("passport_series") == null || request.getParameter("passport_number") == null
                || request.getParameter("name") == null || request.getParameter("surname") == null) {
            //// TODO: 24.11.2016 write fine error messages
            response.sendRedirect("/signup?error=data_error");
        } else {
            if (!request.getParameter("password").equals(request.getParameter("repeated_password"))) {
                response.sendRedirect("/signup?error=passwords_dont_match");
            } else {
                if (userService.emailAlreadyExists(request.getParameter("email").toLowerCase())) {
                    response.sendRedirect("/signup?error=user_already_exists");
                } else {
                    if (!userService.passportDataIsValid(request.getParameter("passport_series"), request.getParameter("passport_num"))) {
                        response.sendRedirect("/signup?error=invalid_passport_data");
                    } else {
                        if (userService.samePassportExists(request.getParameter("passport_series"), request.getParameter("passport_num"))) {
                            response.sendRedirect("/signup?error=passport_already_exists");
                        } else {
                            try {
                                User currentNewUser = userService.createNewUser(
                                        Helper.getHashedString(request.getParameter("password")),
                                        request.getParameter("email").toLowerCase(),
                                        Role.USER,
                                        Integer.parseInt(request.getParameter("timezone_offset")),
                                        request.getParameter("passport_series"),
                                        request.getParameter("passport_num"),
                                        request.getParameter("name"),
                                        request.getParameter("surname"),
                                        request.getParameter("patronymic"),
                                        LocalDate.parse(request.getParameter("birthday_date")));
                                request.getSession().setAttribute("user", currentNewUser);
                                response.sendRedirect("/news");
                            } catch (DateTimeParseException e) {
                                response.sendRedirect("/signup?error=data_error");
                            }
                        }
                    }
                }
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> root = new HashMap<>();
        root.put("error", request.getParameter("error"));
        Helper.render(request, response, "signup.ftl", root);
    }
}
