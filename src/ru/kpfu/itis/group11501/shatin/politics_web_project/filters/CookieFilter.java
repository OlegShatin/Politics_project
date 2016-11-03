package ru.kpfu.itis.group11501.shatin.politics_web_project.filters;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.CookieMaster;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import javax.jws.soap.SOAPBinding;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Oleg Shatin
 *         11-501
 */
@WebFilter(filterName = "CookieFilter")
public class CookieFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        User currentUser = CookieMaster.getUser(request);
        if (currentUser == null) {
            chain.doFilter(req, resp);
        } else {
            request.getSession().setAttribute("user", currentUser);
            response.sendRedirect("/news");
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
