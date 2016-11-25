package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls;

import com.sun.xml.internal.bind.v2.TODO;
import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ConnectionSingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.UserRepository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
            statement.setString(1, email.toLowerCase());
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
            statement.setString(1,  email.toLowerCase());
            statement.setString(2, hashed_password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containsThisEmail(String email) {
        try {
            PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                    "select * FROM users WHERE users.email LIKE ?");
            statement.setString(1, email.toLowerCase());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean samePassportExists(String passportSeries, String passportNum) {
        try {
            PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                    "select * FROM users WHERE users.passport_series LIKE ? AND users.passport_number LIKE ?");
            statement.setString(1, passportSeries);
            statement.setString(2, passportNum);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addNewUser(String password, String email, Role role, int timezoneOffset, String passportSeries, String passportNum, String name, String surname, String patronymic, LocalDate birthdayDate) {
        try {
            PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                    "INSERT INTO users(password_hash, email, role, timezone, passport_series, passport_number," +
                            "name, surname, patronymic, birthday) VALUES (?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, password);
            statement.setString(2, email.toLowerCase());
            statement.setString(3, role.name());
            statement.setInt(4, timezoneOffset);
            statement.setString(5,  passportSeries);
            statement.setString(6, passportNum);
            statement.setString(7, name);
            statement.setString(8, surname);
            statement.setString(9, patronymic);
            statement.setDate(10, Date.valueOf(birthdayDate.toString()));
            return 0 < statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUserById(long userId) {
        PreparedStatement statement = null;
        User result = null;
        try {
            statement = ConnectionSingleton.getConnection().prepareStatement(
                    "select * FROM users WHERE users.id = ?");
            statement.setLong(1, userId);
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
    public boolean updateEmail(long userId, String email) {
        try {
            PreparedStatement statement
                    = ConnectionSingleton.getConnection()
                    .prepareStatement("UPDATE users SET email = ? WHERE id = ?");
            statement.setString(1, email.toLowerCase());
            statement.setLong(2, userId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePassword(long userId, String hashedPassword) {
        try {
            PreparedStatement statement
                    = ConnectionSingleton.getConnection()
                    .prepareStatement("UPDATE users SET password_hash = ? WHERE id = ?");
            statement.setString(1, hashedPassword);
            statement.setLong(2, userId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User createUserLikeResultSet(ResultSet resultSet) throws SQLException {
        return createUserLikeResultSetWithCustomIdColumnName(resultSet, "id");

    }

    User createUserLikeResultSetWithCustomIdColumnName(ResultSet resultSet, String idColumnName) throws SQLException {
        return new User(resultSet.getString("password_hash"),
                resultSet.getString("email"),
                resultSet.getLong(idColumnName),
                Role.valueOf(resultSet.getString("role")),
                ZoneOffset.ofHours(resultSet.getInt("timezone")),
                LocalDate.of(resultSet.getDate("birthday").getYear(),resultSet.getDate("birthday").getMonth() + 1,resultSet.getDate("birthday").getDay()),
                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getString("patronymic"));
    }

}
