package ru.kpfu.itis.group11501.shatin.politics_web_project.helpers;


import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Message;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class ContainerOfUsers {
    public Message getMessage() {
        return message;
    }

    public User getPerson() {
        return user;
    }

    private Message message;

    public ContainerOfUsers(Message message, User user) {
        this.message = message;
        this.user = user;
    }

    private User user;
}
