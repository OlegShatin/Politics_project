package ru.kpfu.itis.group11501.shatin.politics_web_project.services;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Article;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Comment;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.CommentNode;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import sun.reflect.generics.tree.Tree;

import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface NewsService {
    Article getArticle(long articleID, User user);

    Comment addComment(Comment newComment);

    List<CommentNode> getCommentsOfArticleForUser(Article article, User user);

    List<Article> getArticlesForUser(User user);
}
