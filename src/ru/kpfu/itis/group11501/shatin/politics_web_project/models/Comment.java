package ru.kpfu.itis.group11501.shatin.politics_web_project.models;

import java.time.OffsetDateTime;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class Comment {
    private Long id;
    private Long parentCommentID;
    private long articleID;
    private long userID;
    private String text;
    private int rating;
    private OffsetDateTime publicationDateTime;

    public Comment(Long parentCommentID, long articleID, long userID, String text) {
        this.id = null;
        this.parentCommentID = parentCommentID;
        this.articleID = articleID;
        this.userID = userID;
        this.text = text;
    }

    public Comment(Long parentCommentID, long articleID, long userID, String text, OffsetDateTime publicationDateTime) {
        this(parentCommentID, articleID, userID, text);
        this.publicationDateTime = publicationDateTime;
    }

    public Comment(Long id, Long parentCommentID, long articleID, long userID, String text, OffsetDateTime publicationDateTime, int rating) {
        this(parentCommentID, articleID, userID, text, publicationDateTime);
        this.id = id;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public Long getParentCommentID() {
        return parentCommentID;
    }

    public long getArticleID() {
        return articleID;
    }

    public long getUserID() {
        return userID;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public OffsetDateTime getPublicationDateTime() {
        return publicationDateTime;
    }
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", parentCommentID=" + parentCommentID +
                ", articleID=" + articleID +
                ", userID=" + userID +
                ", text='" + text + '\'' +
                ", rating=" + rating +
                ", publicationDateTime=" + publicationDateTime +
                '}';
    }


}
