package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ConnectionSingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Article;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class NewsRepositoryImpl implements NewsRepository {
    @Override
    public Article getArticleByIDForUser(long articleID, User user) {
        PreparedStatement statement = null;
        Article result = null;
        try {
            statement = ConnectionSingleton.getConnection().prepareStatement(
                    "select * FROM news WHERE news.id = ?");
            statement.setLong(1, articleID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                result = createArticleByResultSetForUser(resultSet, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Article> getArticlesForUser(User user) {
        PreparedStatement statement = null;
        List<Article> result = null;
        try {
            statement = ConnectionSingleton.getConnection().prepareStatement(
                    "select * FROM news ORDER BY news.datetime DESC ");
            ResultSet resultSet = statement.executeQuery();
            result = new ArrayList<>();
            while (resultSet.next()){
                result.add(createArticleByResultSetForUser(resultSet, user));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    private Article createArticleByResultSetForUser(ResultSet resultSet, User user) throws SQLException {
        System.out.println( );
        return new Article(resultSet.getLong("id"), resultSet.getString("headline"),
                resultSet.getString("content"),
                OffsetDateTime.ofInstant(resultSet.getTimestamp("datetime").toInstant(), ZoneOffset.ofHours(resultSet.getTimestamp("datetime").getTimezoneOffset()/60))
                        .withOffsetSameInstant(user.getTimezoneOffset()));
    }
}
