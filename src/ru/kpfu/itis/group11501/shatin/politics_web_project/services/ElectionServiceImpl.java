package ru.kpfu.itis.group11501.shatin.politics_web_project.services;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Election;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.CandidateRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.CandidateRepositoryImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.ElectionRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.ElectionRepositoryImpl;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class ElectionServiceImpl implements ElectionService {
    private ElectionRepository electionRepository;
    private CandidateRepository candidateRepository;

    public ElectionServiceImpl() {
        electionRepository = new ElectionRepositoryImpl();
        candidateRepository = new CandidateRepositoryImpl();
    }

    @Override
    public Election getCurrentElectionForUser(User user) {
        Election result = electionRepository.getCurrentRawElectionForUser(user);
        if (result != null) {
            result.getCandidates().addAll(candidateRepository.getCandidatesForElection(result));
        }
        return result;
    }

    @Override
    public boolean userVotedOnElection(User user, Election election) {
        return electionRepository.userVotedOnElection(user, election);
    }

    @Override
    public boolean addVoteForCandidate(User user, Election election, Long votedCandidateId) {
        if (!userVotedOnElection(user, election)) {
            for (Candidate candidate : election.getCandidates()) {
                if (candidate.getId().longValue() == votedCandidateId.longValue()) {
                    //we dont watch who chooses this candidate
                    if (electionRepository.addVoteForCandidate(election, candidate)) {
                        electionRepository.addVotedUser(election, user);
                        return true;
                    } else
                        return false;

                }
            }
            return false;
        } else {
            return false;
        }
    }
}
