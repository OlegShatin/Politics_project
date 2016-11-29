package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls;

import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.AdminRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class AdminRepositorySingleton implements AdminRepository {
    private static AdminRepositorySingleton ourInstance = new AdminRepositorySingleton();

    public static AdminRepositorySingleton getInstance() {
        return ourInstance;
    }

    private AdminRepositorySingleton() {
    }

    @Override
    public Map<String, Object> doQuery(String method, String table, String field, String value, String filter, Map<String, String[]> data) {
        Map<String, Object> result = new HashMap<>();
        if (method != null && table != null){
            if (method.equals("get")){

            }
        }
        return result;
    }
}
