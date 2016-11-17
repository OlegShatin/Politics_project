package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ConnectionSingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Election;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.ElectionType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class CandidateRepositoryImpl implements CandidateRepository {
    @Override
    public List<Candidate> getCandidatesForElection(Election election) {
        List<Candidate> result = new ArrayList<>();
        try {
            if (election.getType() == ElectionType.PARLIAMENT) {
                PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                        "SELECT * FROM elections WHERE ? BETWEEN (start_time, finish_time)");
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    result.add(createCandidatePartyByResultSet(resultSet));
                }
            } else {
                if (election.getType() == ElectionType.PRESIDENT) {
                    PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                            "SELECT * FROM elections WHERE ? BETWEEN (start_time, finish_time)");
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        result.add(createCandidatePresidentByResultSet(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Candidate createCandidatePresidentByResultSet(ResultSet resultSet) {
        return createCandidateByResultSet(resultSet, false);
    }
    private Candidate createCandidatePartyByResultSet(ResultSet resultSet) {
        return createCandidateByResultSet(resultSet, true);
    }
    private Candidate createCandidateByResultSet(ResultSet resultSet, boolean isParty){
        return new Candidate(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("info"),
                resultSet.getString("achievments"),
                resultSet.getString("election_program"),
                resultSet.getString("image_src"),
                resultSet.getString("agent_id"),
                //// TODO: 17.11.2016 make entity Party
                isParty ? new Party(resultSet.getLong("party_id"),
                        resultSet.getInt("seats_in_parliament"),
                        //// TODO: 17.11.2016 make entity Supporter and new query to supporter table 
                        resultSet.
                        ));
    }
}
