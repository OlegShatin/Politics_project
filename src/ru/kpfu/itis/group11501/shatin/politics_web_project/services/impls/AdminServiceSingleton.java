package ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.AdminRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls.AdminRepositorySingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.AdminService;

import java.util.Map;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class AdminServiceSingleton implements AdminService {
    private static AdminServiceSingleton ourInstance = new AdminServiceSingleton();
    private AdminRepository adminRepository;

    public static AdminServiceSingleton getInstance() {
        return ourInstance;
    }

    private AdminServiceSingleton() {
        adminRepository = AdminRepositorySingleton.getInstance();
    }

    @Override
    public Map<String, Object> doQuery(String method, String table, String field, String value, String filter, Map<String, String[]> data) {
        return adminRepository.doQuery(method, table, field, value, filter, data);
    }
}
