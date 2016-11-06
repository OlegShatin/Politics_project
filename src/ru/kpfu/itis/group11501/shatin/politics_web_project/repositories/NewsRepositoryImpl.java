package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ConnectionSingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Article;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class NewsRepositoryImpl implements NewsRepository {
    @Override
    public Article getArticleByID(long articleID) {
        PreparedStatement statement = null;
        Article result = null;
        try {
            statement = ConnectionSingleton.getConnection().prepareStatement(
                    "select * FROM news WHERE news.id LIKE ?");
            statement.setLong(1, articleID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                result = new Article(resultSet.getLong("id"), resultSet.getString("headline"),
                        resultSet.getString("content"),
                        OffsetDateTime.of(resultSet.getTimestamp("datetime").toLocalDateTime(), ZoneOffset.ofHours(resultSet.getTimestamp("datetime").getTimezoneOffset())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
