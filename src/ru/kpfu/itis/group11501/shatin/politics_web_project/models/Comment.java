package ru.kpfu.itis.group11501.shatin.politics_web_project.models;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class Comment {
    private long id;
    private long parentCommentID;
    private long articleID;
    private long userID;
    private String text;
    private int rating;

    public Comment(long id, long parentCommentID, long articleID, long userID, String text, int rating) {
        this(parentCommentID,articleID,userID,text);
        this.id = id;
        this.rating = rating;
    }


//// TODO: 05.11.2016 make date after DB will be made

    public long getId() {
        return id;
    }

    public long getParentCommentID() {
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

    public Comment(long parentCommentID, long articleID, long userID, String text) {

        this.parentCommentID = parentCommentID;
        this.articleID = articleID;
        this.userID = userID;
        this.text = text;
    }

}
