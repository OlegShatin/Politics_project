package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import com.sun.xml.internal.bind.v2.TODO;
import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ConnectionSingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;

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
                result = createUserLikeResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean userExists(String email, String hashed_password) {
        try {
            PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                    "select * FROM users WHERE users.email LIKE ? AND users.password_hash LIKE ?");
            statement.setString(1, "%" + email.toLowerCase() + "%");
            statement.setString(2, "%" + hashed_password + "%");
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    private User createUserLikeResultSet(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getString("password_hash"), resultSet.getString("email"),
                resultSet.getLong("id"), Role.valueOf(resultSet.getString("role")),
                ZoneOffset.ofHours(resultSet.getInt("timezone")));
    }
}
