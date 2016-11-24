package ru.kpfu.itis.group11501.shatin.politics_web_project.helpers;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class Helper {
    public static void render(HttpServletRequest request, HttpServletResponse response, String ftlName, HashMap<String, Object> root) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
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
            StringBuffer  hexString = new StringBuffer();
            passbyte = rawString.getBytes("UTF-8");
            passbyte = md.digest(passbyte);
            for (int i = 0; i < passbyte.length; i++) {
                hexString.append(Integer.toHexString(0xFF & passbyte[i]));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void uploadImage(Part filePart, String fileName) {
        try {
            File file = new File("B:/storage/politics_project/" + fileName + ".jpg");
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            InputStream filecontent = filePart.getInputStream();
            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.close();
            filecontent.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
