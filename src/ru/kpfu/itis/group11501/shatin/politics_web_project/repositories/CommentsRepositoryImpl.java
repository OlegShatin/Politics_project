package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ConnectionSingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Article;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Comment;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.CommentNode;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.CommentNodeImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class CommentsRepositoryImpl implements CommentsRepository {
    /**
     * @param newComment
     * @return the same updated comment with id and rating. if comment already has
     * actually id, it will be updated and returned. if comment has not actually (valid)
     * id, it will be added to db  like a new comment
     */
    @Override
    public Comment addNewComment(Comment newComment) {
        PreparedStatement statement = null;
        Comment result = null;
        try {
            statement = ConnectionSingleton.getConnection().prepareStatement(
                    "SELECT id FROM comments WHERE ? = comments.id");
            statement.setLong(1, newComment.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = straightUpdateComment(newComment);
            } else {
                statement = ConnectionSingleton.getConnection().prepareStatement(
                        "INSERT INTO comments(user_id, article_id, parent_comment_id, comment_text, publicaton_date) " +
                                "VALUES (?,?,?,?,?)"
                );
                statement.setLong(1, newComment.getUserID());
                statement.setLong(2, newComment.getArticleID());
                statement.setLong(3, newComment.getParentCommentID());
                statement.setString(4, newComment.getText());
                statement.setTimestamp(5, new Timestamp(newComment.getPublicationDateTime().toInstant().toEpochMilli()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<? extends CommentNode> getCommentsForArcticleSortedByRating(Article article) {
        List<CommentNode> result = null;
        PreparedStatement statement = null;
        try {
            statement = ConnectionSingleton.getConnection().prepareStatement(
                    "SELECT * FROM comments WHERE ? = comments.article_id AND parent_comment_id = NULL OR " +
                            "comments.parent_comment_id = -1 ORDER BY rating DESC, publicaton_date ASC");
            statement.setLong(1, article.getId());
            ResultSet resultSet = statement.executeQuery();
            result = new ArrayList<>();
            while (resultSet.next()){
                //// TODO: 07.11.2016 move creating comment to another mathod
                result.add(new CommentNodeImpl(new Comment(resultSet.getLong("id"), resultSet.getLong("parent_comment_id"),
                        resultSet.getLong("article_id"),resultSet.getLong("user_id"),resultSet.getString("comment_text"),
                        OffsetDateTime.of(resultSet.getTimestamp("publication_datetime").toLocalDateTime(),
                                ZoneOffset.ofHours(resultSet.getTimestamp("publication_datetime").getTimezoneOffset())),
                        resultSet.getInt("rating"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Comment straightUpdateComment(Comment newComment) throws SQLException {
        PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                "UPDATE comments " +
                        "SET  parent_comment_id = ?, article_id = ?, user_id = ?," +
                        " comment_text = ?, publicaton_date = ?, rating = ? WHERE id = ?;"
        );
        statement.setLong(1, newComment.getParentCommentID());
        statement.setLong(2, newComment.getArticleID());
        statement.setLong(3, newComment.getUserID());
        statement.setString(4, newComment.getText());
        statement.setTimestamp(5, new Timestamp(newComment.getPublicationDateTime().toInstant().toEpochMilli()));
        statement.setInt(6, newComment.getRating());
        statement.setLong(7, newComment.getId());
        statement.executeQuery();
        return newComment;
    }
}
