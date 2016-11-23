package ru.kpfu.itis.group11501.shatin.politics_web_project.helpers;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
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
            /*//// TODO: 23.11.2016 was added wrapper
           BeansWrapper beansWrapper = (BeansWrapper) ObjectWrapper.BEANS_WRAPPER;
            beansWrapper.setExposeFields(true);
            cfg.setObjectWrapper(beansWrapper);;*/
        }
        return cfg;
    }
}
