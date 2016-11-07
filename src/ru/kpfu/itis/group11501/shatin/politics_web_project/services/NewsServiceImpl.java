package ru.kpfu.itis.group11501.shatin.politics_web_project.services;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Article;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Comment;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.CommentNode;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.CommentsRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.CommentsRepositoryImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.NewsRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.NewsRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class NewsServiceImpl implements NewsService {
    private NewsRepository newsRepository;
    private CommentsRepository commentsRepository;

    public NewsServiceImpl(){
        newsRepository = new NewsRepositoryImpl();
        commentsRepository = new CommentsRepositoryImpl();
    }
    @Override
    public Article getArticle(long articleID) {
        return newsRepository.getArticleByID(articleID);
    }

    @Override
    public Comment addComment(Comment newComment) {
        return commentsRepository.addNewComment(newComment);
    }

    @Override
    public List<CommentNode> getCommentsOfArticle(Article article) {
        List<CommentNode> result = new ArrayList<>();
        result.addAll(commentsRepository.getCommentsForArcticleSortedByRating(article));
        //// TODO: 07.11.2016 put all commets 
        return result;
    }
}
