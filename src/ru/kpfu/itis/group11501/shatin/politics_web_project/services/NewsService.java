package ru.kpfu.itis.group11501.shatin.politics_web_project.services;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Article;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Comment;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.CommentNode;
import sun.reflect.generics.tree.Tree;

import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface NewsService {
    Article getArticle(long articleID);

    Comment addComment(Comment newComment);

    List<CommentNode> getCommentsOfArticle(Article article);
}
