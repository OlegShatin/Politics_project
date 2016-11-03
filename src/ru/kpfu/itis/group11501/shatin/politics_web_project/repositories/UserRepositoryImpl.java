package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ConnectionSingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class UserRepositoryImpl implements UserRepository {
    @Override
    public User getUserByEmail(String email) {
        PreparedStatement statement = null;
        User result = null;
        try {
            statement = ConnectionSingleton.getConnection().prepareStatement(
                    "select * FROM users WHERE users.email LIKE ?");
            statement.setString(1, "%" + email.toLowerCase() + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                result = new User();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
