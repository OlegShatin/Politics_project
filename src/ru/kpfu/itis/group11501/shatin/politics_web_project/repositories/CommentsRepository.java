package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Article;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Comment;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.CommentNode;

import java.util.Collection;
import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface CommentsRepository {
    Comment addNewComment(Comment newComment);

    List<? extends CommentNode> getCommentsForArcticleSortedByRating(Article article);
}
