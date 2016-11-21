package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.ConnectionSingleton;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.*;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.CandidateRepository;

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
    /**
     * @param election                 - collect candidates for this election
     * @param includeDefaultCandidates - flag, include technical default candidates ('Empty Ballot' and 'To spoil ballot') or don't
     * @return list of candidates
     */
    @Override
    public List<Candidate> getCandidatesForElection(Election election, boolean includeDefaultCandidates) {
        List<Candidate> result = new ArrayList<>();
        try {
            String toInsert = includeDefaultCandidates ? "" : " AND NOT (candidates.id = 1 OR candidates.id = 2)";
            if (election.getType() == ElectionType.PARLIAMENT) {
                PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                        "SELECT candidates.*, parties.*, parties.id AS party_id FROM candidates_lists JOIN candidates ON (candidates_lists.candidate_id = candidates.id)" +
                                " JOIN parties ON (candidates.party = parties.id)WHERE ? = election_id" + toInsert);
                statement.setLong(1, election.getId());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    result.add(createCandidatePartyByResultSet(resultSet));
                }
            } else {
                if (election.getType() == ElectionType.PRESIDENT) {
                    PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                            "SELECT candidates.* FROM candidates_lists JOIN candidates ON (candidates_lists.candidate_id = candidates.id) WHERE ? = election_id" + toInsert);
                    statement.setLong(1, election.getId());
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

    @Override
    public Candidate getCandidateForAgent(User agent) {
        try {
            PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement(
                    "SELECT candidates.*, parties.*, parties.id AS party_id FROM candidates_lists JOIN candidates ON (candidates_lists.candidate_id = candidates.id)" +
                            " JOIN parties ON (candidates.party = parties.id)WHERE ? = agent_id");
            statement.setLong(1, agent.getID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                resultSet.getLong("party_id");
                if (resultSet.wasNull()) {
                    return createCandidatePresidentByResultSet(resultSet);
                } else {
                    return createCandidatePartyByResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Candidate createCandidatePresidentByResultSet(ResultSet resultSet) throws SQLException {
        return createCandidateByResultSet(resultSet, false);
    }

    private Candidate createCandidatePartyByResultSet(ResultSet resultSet) throws SQLException {
        return createCandidateByResultSet(resultSet, true);
    }

    private Candidate createCandidateByResultSet(ResultSet resultSet, boolean isParty) throws SQLException {
        return new Candidate(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("info"),
                resultSet.getString("achievements"),
                resultSet.getString("election_program"),
                resultSet.getString("image_src"),
                resultSet.getLong("agent_id"),
                isParty ? new Party(resultSet.getLong("party_id"),
                        resultSet.getInt("seats_in_parliament"),
                        getSupportersForPartyByPartyId(resultSet.getLong("party_id"))
                ) : null);
    }

    private List<Supporter> getSupportersForPartyByPartyId(Long partyId) {
        //if got null return null else empty or containing list
        List<Supporter> result = null;
        if (partyId != null) {
            result = new ArrayList<>();
            try {
                PreparedStatement statement = ConnectionSingleton.getConnection().prepareStatement("SELECT * FROM supporters WHERE party_id = ?");
                statement.setLong(1, partyId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    result.add(createSupporterLikeResultSet(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Supporter createSupporterLikeResultSet(ResultSet resultSet) throws SQLException {
        return new Supporter(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getString("image_src"),
                resultSet.getLong("party_id"));
    }
}
