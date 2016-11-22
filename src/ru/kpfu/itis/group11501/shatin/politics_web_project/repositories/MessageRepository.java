package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Message;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface MessageRepository {
    Message addMessage(Message newMessage);
}
