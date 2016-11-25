package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ConnectionSingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Election;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.ElectionType;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.ElectionRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

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
        return getElection(user, true);
    }

    @Override
    public Election getNextRawElectionForUser(User user) {
        return getElection(user, false);
    }

    @Override
    public List<Election> getParliamentRawElectionsBefore(OffsetDateTime moment) {
        return getElectionsBefore(false,true,moment);
    }

    @Override
    public List<Election> getPresidentRawElectionsBefore(OffsetDateTime moment) {
        return getElectionsBefore(true,false,moment);
    }

    private List<Election> getElectionsBefore(boolean notIncludeParliament, boolean notIncludePresident, OffsetDateTime moment) {
        List<Election> result = new ArrayList<>();
        try {
            String query="SELECT * FROM elections ";
            String toInsert = "";
            if (notIncludeParliament || notIncludePresident || moment != null){
                toInsert = "WHERE ";
                if (notIncludeParliament){
                    toInsert += "(NOT elections.type = 'PARLIAMENT')";
                }
                if (notIncludeParliament && notIncludePresident){
                    toInsert +=" AND ";
                }
                if (notIncludePresident){
                    toInsert += "(NOT elections.type = 'PRESIDENT') ";
                }
                if ((notIncludeParliament || notIncludePresident) && moment != null){
                    toInsert+=" AND ";
                }
                if (moment != null){
                    toInsert +="(? > finish_time) ";
                }
            }
            query += toInsert + " ORDER BY finish_time";
            PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(query);
            if (moment != null) {
                statement.setTimestamp(1, new Timestamp(moment.toInstant().toEpochMilli()));
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                result.add(createElectionByResultSetForUser(resultSet, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Election> getAllRawElectionsBefore(OffsetDateTime moment) {
        return getElectionsBefore(false,false,moment);
    }

    @Override
    public int getTotalBallotsForElection(Election election) {
        try {
            PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                    "SELECT count(*) FROM votes WHERE ? = election_id");
            statement.setLong(1,election.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Election getElection(User user, boolean requiredCurrentElection) {
        Election result = null;
        try {
            String toInsert = requiredCurrentElection? "BETWEEN start_time AND" : "<";
            PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                    "SELECT * FROM elections WHERE ? " + toInsert + " finish_time ORDER BY finish_time");
            //hard for understanding:
            statement.setTimestamp(1, new Timestamp(OffsetDateTime.now().withOffsetSameInstant(user.getTimezoneOffset())
                    .withOffsetSameLocal(OffsetDateTime.now().getOffset()).toInstant().toEpochMilli()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                //// TODO: 22.11.2016 some bug with default server offset
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
            statement.setLong(1,user.getId());
            statement.setLong(2, election.getId());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addVoteForCandidate(Election election, Candidate candidate) {
        try {
            //check is this candidate in list of this election
            //another solution: UPDATE candidates_lists SET votes = votes+1 WHERE ? = election_id AND ? = candidate_id
            PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                    "SELECT * FROM candidates_lists WHERE ? = election_id AND ? = candidate_id");
            statement.setLong(1,election.getId());
            statement.setLong(2, candidate.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                statement = ConnectionSingleton.getConnection().prepareStatement(
                        "UPDATE candidates_lists SET votes = ? WHERE ? = election_id AND ? = candidate_id");
                statement.setInt(1, resultSet.getInt("votes") + 1);
                statement.setLong(2,election.getId());
                statement.setLong(3, candidate.getId());
                statement.executeUpdate();
                return true;
            } else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void addVotedUser(Election election, User user) {
        if (!userVotedOnElection(user, election)){
            try {
                PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                        "INSERT INTO votes(user_id, election_id) VALUES (?,?)");
                statement.setLong(1,user.getId());
                statement.setLong(2, election.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private Election createElectionByResultSetForUser(ResultSet resultSet, User user) throws SQLException {
        return new Election(resultSet.getLong("id"),
                ElectionType.valueOf(resultSet.getString("type")),
                OffsetDateTime.ofInstant(resultSet.getTimestamp("start_time").toInstant(),
                        ZoneOffset.ofHours(3))
                        .withOffsetSameLocal(user == null ? ZoneOffset.UTC : user.getTimezoneOffset()),
                OffsetDateTime.ofInstant(resultSet.getTimestamp("finish_time").toInstant(),
                        ZoneOffset.ofHours(3))
                        .withOffsetSameLocal(user == null ? ZoneOffset.UTC : user.getTimezoneOffset()), new ArrayList<Candidate>());

    }
}
