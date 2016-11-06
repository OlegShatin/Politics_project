package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Comment;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface CommentsRepository {
    Comment addNewComment(Comment newComment);
}
