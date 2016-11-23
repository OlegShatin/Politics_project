package ru.kpfu.itis.group11501.shatin.politics_web_project.models;

import java.time.OffsetDateTime;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class Message implements Comparable{
    private Long id;
    private Long senderId;
    private Long recipientId;
    private String message_text;
    private OffsetDateTime sendingTime;

    public Long getId() {
        return id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public String getMessageText() {
        return message_text;
    }

    public OffsetDateTime getSendingTime() {
        return sendingTime;
    }

    public Message(Long id, Long senderId, Long recipientId, String message_text, OffsetDateTime sendingTime) {
        this(senderId,recipientId,message_text,sendingTime);
        this.id = id;

    }

    public Message(Long senderId, Long recipientId, String message_text, OffsetDateTime sendingTime) {

        this.senderId = senderId;
        this.recipientId = recipientId;
        this.message_text = message_text;
        this.sendingTime = sendingTime;
    }

    @Override
    public int compareTo(Object o) {
        return -this.getSendingTime().compareTo(((Message)o).getSendingTime());
    }
}
