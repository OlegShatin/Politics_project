package ru.kpfu.itis.group11501.shatin.politics_web_project.services;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Article;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface NewsService {
    Article getArticle(long articleID);
}
