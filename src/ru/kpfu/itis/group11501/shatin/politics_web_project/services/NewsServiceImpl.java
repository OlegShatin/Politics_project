package ru.kpfu.itis.group11501.shatin.politics_web_project.services;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Article;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.NewsRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.NewsRepositoryImpl;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class NewsServiceImpl implements NewsService {
    private NewsRepository newsRepository;
    public NewsServiceImpl(){
        newsRepository = new NewsRepositoryImpl();
    }
    @Override
    public Article getArticle(long articleID) {
        return newsRepository.getArticleByID(articleID);
    }
}
