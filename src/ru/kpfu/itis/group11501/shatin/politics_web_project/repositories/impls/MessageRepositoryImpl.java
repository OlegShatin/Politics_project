package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ConnectionSingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Message;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class MessageRepositoryImpl implements ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.MessageRepository {
    private UserRepositoryImpl userRepository;

    public MessageRepositoryImpl() {
        userRepository = new UserRepositoryImpl();
    }

    @Override
    public Message addMessage(Message newMessage, User user) {
        try {
            if (userWithSameIdExists(newMessage.getRecipientId()) && userWithSameIdExists(newMessage.getSenderId())) {
                PreparedStatement statement
                        = ConnectionSingleton.getConnection()
                        .prepareStatement("INSERT INTO messages(sender_id, recepient_id, message_text, sending_time) VALUES (?,?,?,?);");
                statement.setLong(1, newMessage.getSenderId());
                statement.setLong(2, newMessage.getRecipientId());
                statement.setString(3, newMessage.getMessageText());
                statement.setTimestamp(4, new Timestamp(newMessage.getSendingTime().toInstant().toEpochMilli()));
                statement.executeUpdate();
                statement = ConnectionSingleton.getConnection()
                        .prepareStatement("SELECT messages.*, users.timezone FROM messages " +
                                "JOIN users ON (messages.sender_id = users.id)" +
                                " WHERE sender_id = ? AND recepient_id = ? AND messages.message_text = ? AND messages.sending_time = ?");
                statement.setLong(1, newMessage.getSenderId());
                statement.setLong(2, newMessage.getRecipientId());
                statement.setString(3, newMessage.getMessageText());
                statement.setTimestamp(4, new Timestamp(newMessage.getSendingTime().toInstant().toEpochMilli()));
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return createMessageLikeResultSetForUser(resultSet, user);
                } else
                    return null;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean existsConversation(User user, User anotherUser) {
        try {
            PreparedStatement statement
                    = ConnectionSingleton.getConnection().prepareStatement("SELECT * FROM messages" +
                    " WHERE (sender_id = ? AND recepient_id = ?) OR (sender_id = ? AND recepient_id = ?)");
            statement.setLong(1, user.getId());
            statement.setLong(2, anotherUser.getId());
            statement.setLong(3, anotherUser.getId());
            statement.setLong(4, user.getId());
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Map<Message, User> getLastMessagesWithOffsetForUser(User user, int offsetRows, int limitRows) {
        Map<Message, User> result = new HashMap<>();
        try {
            PreparedStatement statement
                    = ConnectionSingleton.getConnection().prepareStatement(
                    "SELECT * FROM (SELECT m.id AS message_id, m.*,users.id AS other_user,users.* " +
                            "FROM messages m JOIN  users ON (m.recepient_id = users.id) WHERE m.sender_id = ? " +
                            "UNION SELECT m.id AS message_id, m.*,users.id AS other_user,users.*" +
                            " FROM messages m JOIN  users ON (m.sender_id = users.id) WHERE m.recepient_id = ?)" +
                            " AS upq " +
                            "WHERE upq.sending_time = " +
                            "(SELECT MAX(messages.sending_time) FROM messages " +
                            "WHERE (messages.recepient_id = upq.recepient_id AND messages.sender_id = upq.sender_id) " +
                            "OR(messages.recepient_id = upq.sender_id AND messages.sender_id = upq.recepient_id)) " +
                            "ORDER BY upq.sending_time DESC OFFSET ? LIMIT ?");
            statement.setLong(1, user.getId());
            statement.setLong(2, user.getId());
            statement.setInt(3, offsetRows);
            statement.setInt(4, limitRows);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
//todo set message time by user param
                result.put(createMessageLikeResultSetWithCustomIdColumnNameForUser(resultSet, "message_id", user),
                        userRepository.createUserLikeResultSetWithCustomIdColumnName(resultSet, "other_user"));

            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getCountOfRowsFromLastMessagesTableForUser(User user) {
        try {
            PreparedStatement statement
                    = ConnectionSingleton.getConnection().prepareStatement(
                    "SELECT count(*) " +
                            "FROM (SELECT m.id AS message_id, m.*,users.id AS other_user,users.* " +
                            "FROM messages m JOIN  users ON (m.recepient_id = users.id) WHERE m.sender_id = ? " +
                            "UNION SELECT m.id AS message_id, m.*,users.id AS other_user,users.*" +
                            " FROM messages m JOIN  users ON (m.sender_id = users.id) WHERE m.recepient_id = ?)" +
                            " AS upq " +
                            "WHERE upq.sending_time = " +
                            "(SELECT MAX(messages.sending_time) FROM messages " +
                            "WHERE (messages.recepient_id = upq.recepient_id AND messages.sender_id = upq.sender_id) " +
                            "OR(messages.recepient_id = upq.sender_id AND messages.sender_id = upq.recepient_id))"
            );
            statement.setLong(1, user.getId());
            statement.setLong(2, user.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Message> getMessagesBetweenUsersSortedByTimeDescForUser(User user, User otherUser) {

        try {
            if (user != null && otherUser != null && userWithSameIdExists(otherUser.getId()) && existsConversation(user, otherUser)) {
                PreparedStatement statement
                        = ConnectionSingleton.getConnection()
                        .prepareStatement("SELECT * FROM messages " +
                                "WHERE (sender_id = ? AND recepient_id = ?) " +
                                "OR (recepient_id = ? AND sender_id = ?) " +
                                "ORDER BY sending_time DESC");
                statement.setLong(1, user.getId());
                statement.setLong(2, otherUser.getId());
                statement.setLong(3, user.getId());
                statement.setLong(4, otherUser.getId());
                ResultSet resultSet = statement.executeQuery();
                List<Message> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(createMessageLikeResultSetForUser(resultSet, user));
                }
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Message createMessageLikeResultSetWithCustomIdColumnNameForUser(ResultSet resultSet, String idColumnName, User user) throws SQLException {
        return new Message(
                resultSet.getLong(idColumnName),
                resultSet.getLong("sender_id"),
                resultSet.getLong("recepient_id"),
                resultSet.getString("message_text"),
                OffsetDateTime.ofInstant(resultSet.getTimestamp("sending_time").toInstant(),
                        ZoneOffset.ofHours(resultSet.getTimestamp("sending_time").getTimezoneOffset() / 60))
                        .withOffsetSameInstant(user.getTimezoneOffset())
        );
    }


    private Message createMessageLikeResultSetForUser(ResultSet resultSet, User user) throws SQLException {
        return createMessageLikeResultSetWithCustomIdColumnNameForUser(resultSet, "id", user);
    }

    private boolean userWithSameIdExists(Long id) throws SQLException {
        PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement("SELECT * FROM users WHERE id = ?");
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
}
