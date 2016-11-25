package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import java.util.Map;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface AdminRepository {
    Map<String,Object> doQuery(String method, String table, String field, String value, String filter, Map<String, String[]> data);
}
