package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Article;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Comment;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.CommentNode;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface CommentsRepository {
    Comment addNewComment(Comment newComment);

    List<CommentNode> getCommentsForArticleSortedByRatingForUser(Article article, User user);

    List<CommentNode> getChildrenCommentsSortedByRatingForUser(Comment comment, User user);

    Comment getComment(long commentId, User user);

    void changeCommentRating(Comment comment, int rate);
}
