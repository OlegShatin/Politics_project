package ru.kpfu.itis.group11501.shatin.politics_web_project.servlets;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.CandidateService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserService;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.CandidateServiceImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author Oleg Shatin
 *         11-501
 */
@WebServlet(name = "ProfileServlet")
@MultipartConfig
public class ProfileServlet extends HttpServlet {
    private CandidateService candidateService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        candidateService = new CandidateServiceImpl();
        userService = new UserServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = userService.authorize(request.getSession().getAttribute("user"));
        if (user != null && user.getRole() != Role.GUEST) {
            if (user.getRole() == Role.USER) {
                boolean tryEmailUpdate = false;
                boolean emailUsed = false;
                boolean emailUpdated = false;
                boolean tryPasswordUpdate = false;
                boolean passwordUpdated = false;
                boolean oldPasswordsMatch = false;
                String email = request.getParameter("email");
                if (email != null) {
                    if (userService.emailAlreadyExists(email)) {
                        emailUsed = true;
                    } else {
                        tryEmailUpdate = true;
                        emailUpdated = userService.updateEmail(user.getId(), email);
                        if (emailUpdated) {
                            request.getSession().setAttribute("user", userService.getUser(user.getId()));
                            user = userService.authorize(request.getSession().getAttribute("user"));
                        }
                    }
                }
                String oldPassword = request.getParameter("old_password");
                String password = request.getParameter("password");
                if (oldPassword != null) {
                    tryPasswordUpdate = true;
                    oldPasswordsMatch = user.getHashedPassword().equals(Helper.getHashedString(oldPassword));
                    if (oldPasswordsMatch) {
                        if (password != null) {
                            passwordUpdated = userService.updatePassword(user.getId(), Helper.getHashedString(password));
                            if (passwordUpdated){
                                request.getSession().setAttribute("user", userService.getUser(user.getId()));
                                user = userService.authorize(request.getSession().getAttribute("user"));
                            }
                        }
                    }
                }
                String params = "";
                if (emailUsed) {
                    params += "email_upd=used&";
                } else {
                    if (tryEmailUpdate && emailUpdated) {
                        params += "email_upd=success&";
                    } else {
                        if (tryEmailUpdate)
                            params += "email_upd=fail&";
                    }
                }
                if (tryPasswordUpdate) {
                    if (!oldPasswordsMatch) {
                        params += "pass_upd=not_match";
                    } else {
                        if (!passwordUpdated){
                            params += "pass_upd=fail";
                        } else {
                            params += "pass_upd=success";
                        }
                    }
                }
                response.sendRedirect("/profile?"+params);
            } else {
                if (user.getRole() == Role.AGENT) {
                    Candidate candidate = candidateService.getCandidateForAgent(user);
                    try {
                        String field = request.getParameter("field");
                        String value = request.getParameter("value");
                        Part img = request.getPart("new_image");
                        if (img != null && img.getSize() > 0){
                            Helper.uploadImage(img, "candidates/" + candidate.getId());
                            field="image_src";
                            value="img/candidates/"+candidate.getId()+".jpg";
                        }
                        if (field != null && value != null){
                            StringBuilder sb = new StringBuilder(field);
                            sb.setCharAt(0,field.toUpperCase().charAt(0));
                            field = sb.toString();
                            Method method = candidateService.getClass().getMethod("update"+field, long.class , Class.forName("java.lang.String"));
                            if ((boolean)method.invoke(candidateService, new Object[] {candidate.getId(), value})) {
                                response.sendRedirect("/profile?update_success=" + field.toLowerCase());
                            } else {
                                response.sendRedirect("/profile?error=update_failed");
                            }
                        }
                        else {
                            response.sendRedirect("/profile?error=parameters_error");
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                        response.sendRedirect("/profile?error=parameters_error");
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                        response.sendRedirect("/profile?error=parameters_error");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        response.sendRedirect("/profile?error=parameters_error");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        response.sendRedirect("/profile?error=parameters_error");
                    }
                } else {
                    response.sendRedirect("/news?error=access_denied");
                }
            }
        } else {
            response.sendRedirect("/login");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = userService.authorize(request.getSession().getAttribute("user"));
        HashMap<String, Object> root = new HashMap<>();
        root.put("pass_upd", request.getParameter("pass_upd"));
        root.put("email_upd", request.getParameter("email_upd"));
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
                    Helper.render(request, response, "candidateProfile.ftl", root);
                } else {
                    response.sendRedirect("/404");
                }
            } else {
                if (user.getRole() == Role.ADMIN) {
                    response.sendRedirect("/news?error=access_denied");
                } else
                    response.sendRedirect("/login");
            }
        }
    }
}
