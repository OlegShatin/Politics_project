package ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Article;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Comment;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.CommentNode;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.CommentsRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls.CommentsRepositoryImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.NewsRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls.NewsRepositoryImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.NewsService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

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
    public Article getArticle(long articleID, User user) {
        return newsRepository.getArticleByIDForUser(articleID, user);
    }

    @Override
    public Comment addComment(Comment newComment) {
        return commentsRepository.addNewComment(newComment);
    }

    @Override
    public List<CommentNode> getCommentsOfArticleForUser(Article article, User user) {
        List<CommentNode> result = new ArrayList<>();
        List<CommentNode> firstCommentsNodes = commentsRepository.getCommentsForArticleSortedByRatingForUser(article, user);
        for (CommentNode node: firstCommentsNodes) {
            Stack<CommentNode> stack = new Stack<>();
            stack.push(node);
            List<CommentNode> childrenNodes;
            CommentNode parentNode;
            //DFS: node is first node, collections reverse list from db to save order when it will be pushed to stack
            while (!stack.isEmpty()) {
                childrenNodes = commentsRepository.getChildrenCommentsSortedByRatingForUser(stack.peek().getComment(), user);
                if (!childrenNodes.isEmpty()) {
                    parentNode = stack.pop();
                    parentNode.setChildren(childrenNodes);
                    Collections.reverse(childrenNodes);
                    stack.addAll(childrenNodes);
                } else {
                    stack.pop();
                }
            }
            result.add(node);
        }
        return result;
    }

    @Override
    public List<Article> getArticlesForUser(User user, String search, boolean headlinesOnly) {
        return newsRepository.getArticlesForUser(user, search, headlinesOnly);
    }

    @Override
    public Comment getCommentById(long commentId, User user) {
        return commentsRepository.getComment(commentId, user);
    }

    @Override
    public void upRatingForComment(Comment comment) {
        commentsRepository.changeCommentRating(comment, 1);
    }

    @Override
    public void downRatingForComment(Comment comment) {
        commentsRepository.changeCommentRating(comment, -1);
    }
}
