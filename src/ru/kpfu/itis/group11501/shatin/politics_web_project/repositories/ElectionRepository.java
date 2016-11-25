package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Election;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.ResultItem;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface ElectionRepository {
    Election getCurrentRawElectionForUser(User user);

    boolean userVotedOnElection(User user, Election election);

    boolean addVoteForCandidate(Election election, Candidate candidate);

    void addVotedUser(Election election, User user);

    Election getNextRawElectionForUser(User user);

    List<Election> getParliamentRawElectionsBefore(OffsetDateTime moment);

    List<Election> getPresidentRawElectionsBefore(OffsetDateTime moment);

    List<Election> getAllRawElectionsBefore(OffsetDateTime moment);

    int getTotalBallotsForElection(Election election);
}
