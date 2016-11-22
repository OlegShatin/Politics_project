package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ConnectionSingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Message;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class MessageRepositoryImpl implements ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.MessageRepository {
    @Override
    public Message addMessage(Message newMessage) {
        try {
            if (userWithSameIdExists(newMessage.getRecipientId()) && userWithSameIdExists(newMessage.getSenderId())){
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
                    return createMessageLikeResultSet(resultSet);
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

    private Message createMessageLikeResultSet(ResultSet resultSet) throws SQLException {
        return new Message(
                resultSet.getLong("id"),
                resultSet.getLong("sender_id"),
                resultSet.getLong("recepient_id"),
                resultSet.getString("message_text"),
                OffsetDateTime.ofInstant(resultSet.getTimestamp("sending_time").toInstant(),
                        ZoneOffset.ofHours(resultSet.getTimestamp("sending_time").getTimezoneOffset()/60))
                        .withOffsetSameInstant(ZoneOffset.ofHours(resultSet.getInt("timezone")))
                );
    }

    private boolean userWithSameIdExists(Long id) throws SQLException {
        PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement("SELECT * FROM users WHERE id = ?");
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
}
