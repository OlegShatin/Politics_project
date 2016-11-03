package ru.kpfu.itis.group11501.shatin.politics_web_project.helpers;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class Helper {
    public static void render(HttpServletRequest request, HttpServletResponse response, String ftlName, HashMap<String, Object> root) throws IOException {
        response.setContentType("text/html");
        Template tmpl = ConfigSingleton.getConfig(request.getServletContext()).getTemplate(ftlName);
        try {
            tmpl.process(root, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
    public static String getHashedString(String rawString){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] passbyte;
            passbyte = rawString.getBytes("UTF-8");
            passbyte = md.digest(passbyte);
            return new String(passbyte,"UTF-8");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
