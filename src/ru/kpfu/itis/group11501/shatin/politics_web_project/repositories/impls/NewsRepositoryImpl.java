package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ConnectionSingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Article;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.NewsRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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
                    "SELECT * FROM news WHERE news.id = ?");
            statement.setLong(1, articleID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = createArticleByResultSetForUser(resultSet, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Article> getArticlesForUser(User user, String search, boolean headlinesOnly) {
        PreparedStatement statement = null;
        List<Article> result = null;
        try {
            if (search == null) {
                statement = ConnectionSingleton.getConnection().prepareStatement(
                        "SELECT * FROM news ORDER BY news.datetime DESC ");
            } else {
                if (headlinesOnly) {
                    statement = ConnectionSingleton.getConnection().prepareStatement(
                            "SELECT * FROM news WHERE news.headline LIKE ? ORDER BY news.datetime DESC ");
                    statement.setString(1, "%" + search + "%");
                } else {
                    statement = ConnectionSingleton.getConnection().prepareStatement(
                            "SELECT * FROM news WHERE news.headline LIKE ? OR news.content LIKE ? ORDER BY news.datetime DESC ");
                    statement.setString(1, "%" + search + "%");
                    statement.setString(2, "%" + search + "%");
                }
            }
            ResultSet resultSet = statement.executeQuery();
            result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(createArticleByResultSetForUser(resultSet, user));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Article createArticleByResultSetForUser(ResultSet resultSet, User user) throws SQLException {
        return new Article(resultSet.getLong("id"), resultSet.getString("headline"),
                resultSet.getString("content"),
                OffsetDateTime.ofInstant(resultSet.getTimestamp("datetime").toInstant(), ZoneOffset.ofHours(resultSet.getTimestamp("datetime").getTimezoneOffset() / 60))
                        .withOffsetSameInstant(user.getTimezoneOffset()));
    }
}
