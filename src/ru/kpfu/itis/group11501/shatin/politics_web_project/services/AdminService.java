package ru.kpfu.itis.group11501.shatin.politics_web_project.services;

import java.util.Map;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface AdminService {
    Map<String,Object> doQuery(String method, String table, String field, String value, String filter, Map<String, String[]> data);
}
