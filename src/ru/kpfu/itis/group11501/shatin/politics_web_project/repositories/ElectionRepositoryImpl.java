package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ConnectionSingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Election;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.ElectionType;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class ElectionRepositoryImpl implements ElectionRepository {
    /**
     * generate a election frame with info about start and finish time for the same user without info about candidates
     * @param user
     * @return Election object with empty list of candidates
     */
    @Override
    public Election getCurrentRawElectionForUser(User user) {
        Election result = null;
        try {
            PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                    "SELECT * FROM elections WHERE ? BETWEEN (start_time, finish_time)");
            //hard for understanding:
            statement.setTimestamp(1, new Timestamp(OffsetDateTime.now().withOffsetSameInstant(user.getTimezoneOffset())
                    .withOffsetSameLocal(OffsetDateTime.now().getOffset()).toInstant().toEpochMilli()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                result = createElectionByResultSetForUser(resultSet, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean userVotedOnElection(User user, Election election) {
        try {
            PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                    "SELECT * FROM votes WHERE ? = user_id AND ? = election_id");
            statement.setLong(1,user.getID());
            statement.setLong(2, election.getId());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Election createElectionByResultSetForUser(ResultSet resultSet, User user) throws SQLException {
        Election result = new Election(resultSet.getLong("id"),
                ElectionType.valueOf(resultSet.getString("type")),
                OffsetDateTime.ofInstant(resultSet.getTimestamp("start_time").toInstant(),
                        ZoneOffset.ofHours(resultSet.getTimestamp("start_time").getTimezoneOffset()/60))
                        .withOffsetSameLocal(user.getTimezoneOffset()),
                OffsetDateTime.ofInstant(resultSet.getTimestamp("finish_time").toInstant(),
                        ZoneOffset.ofHours(resultSet.getTimestamp("finish_time").getTimezoneOffset()/60))
                        .withOffsetSameLocal(user.getTimezoneOffset()), new ArrayList<Candidate>());
        return result;
    }
}
