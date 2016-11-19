package ru.kpfu.itis.group11501.shatin.politics_web_project.services;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Election;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface ElectionService {
    Election getCurrentElectionForUser(User user);

    boolean userVotedOnElection(User user, Election election);

    boolean addVoteForCandidate(User user, Election election, Long votedCandidateId);

    Election getNextElectionForUser(User user);

    Candidate getCandidate(long candidateNum, Election election);
}
