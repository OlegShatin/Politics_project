package ru.kpfu.itis.group11501.shatin.politics_web_project.helpers;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.ServletContext;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class ConfigSingleton {
    private static Configuration cfg = null;
    public static Configuration getConfig(ServletContext sc) {
        if (cfg == null){
            cfg = new Configuration();
            cfg.setServletContextForTemplateLoading(
                    sc,
                    "/WEB-INF/templates"
            );
            cfg.setTemplateExceptionHandler(
                    TemplateExceptionHandler.HTML_DEBUG_HANDLER
            );
        }
        return cfg;
    }
}
