package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Message;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface MessageRepository {

    Message addMessage(Message newMessage, User user);

    boolean existsConversation(User user, User anotherUser);

    Map<Message, User> getLastMessagesWithOffsetForUser(User user, int offsetRows, int limitRows);

    int getCountOfRowsFromLastMessagesTableForUser(User user);

    List<Message> getMessagesBetweenUsersSortedByTimeDescForUser(User user, User otherUser);
}
