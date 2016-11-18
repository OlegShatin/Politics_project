package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Article;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface NewsRepository {
    public Article getArticleByIDForUser(long articleID, User user);

    List<Article> getArticlesForUser(User user);
}
