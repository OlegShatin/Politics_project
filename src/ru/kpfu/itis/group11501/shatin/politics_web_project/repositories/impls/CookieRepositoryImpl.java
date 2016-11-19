package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ConnectionSingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.CookieRepository;

import javax.servlet.http.Cookie;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class CookieRepositoryImpl implements CookieRepository {
    @Override
    public Cookie addCookie(User currentUser, String cookieName) {
        PreparedStatement statement = null;
        Cookie result = null;
        try {
            statement = ConnectionSingleton.getConnection().prepareStatement(
                    "SELECT * FROM cookies WHERE cookies.user_id = ?");
            statement.setLong(1, currentUser.getID());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = new Cookie(cookieName, updateUserCookie(currentUser));
            } else {
                result = new Cookie(cookieName, insertUserCookie(currentUser));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public long getUserId(String encryptedValue) {
        try {
            PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                    "SELECT * FROM cookies WHERE cookies.value LIKE ?");
            if (!containsValue(encryptedValue, statement)) {
                return -1;
            } else {
                statement.setString(1, encryptedValue);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    return resultSet.getLong("user_id");
                }
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Cookie updateCookie(User user, String cookieName) {
        try {
            return new Cookie(cookieName, updateUserCookie(user));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String insertUserCookie(User currentUser) throws SQLException {
        String result = getUniqueVal(currentUser);
        PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                "INSERT INTO cookies VALUES(?,?)");
        statement.setString(2, result);
        statement.setLong(1, currentUser.getID());
        statement.executeUpdate();
        return result;
    }

    private String updateUserCookie(User currentUser) throws SQLException {
        String result = getUniqueVal(currentUser);
        PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                "UPDATE cookies SET value = ? WHERE cookies.user_id = ?");
        statement.setString(1, result);
        statement.setLong(2, currentUser.getID());
        statement.executeUpdate();
        return result;
    }

    private String getUniqueVal(User currentUser) throws SQLException {
        PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                "SELECT * FROM cookies WHERE cookies.value LIKE ?");
        String result = Helper.getHashedString(currentUser.getEmail() + ZonedDateTime.now().toString());
        while (containsValue(result, statement)) {
            result = Helper.getHashedString(currentUser.getEmail() + ZonedDateTime.now().toString());
        }
        return result;
    }


    private boolean containsValue(String value, PreparedStatement statement) throws SQLException {
        statement.setString(1,"%" + value +"%");
        ResultSet resultSet = statement.executeQuery();
        return (resultSet.next());
    }
}
