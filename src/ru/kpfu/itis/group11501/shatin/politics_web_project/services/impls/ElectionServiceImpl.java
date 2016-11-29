package ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.*;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.CandidateRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls.CandidateRepositoryImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.ElectionRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls.ElectionRepositoryImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.ElectionService;

import java.time.OffsetDateTime;
import java.util.List;

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
            result.getCandidates().addAll(candidateRepository.getCandidatesForElection(result, true));
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

    @Override
    public Election getNextElectionForUser(User user) {
        Election result = electionRepository.getNextRawElectionForUser(user);
        if (result != null) {
            result.getCandidates().addAll(candidateRepository.getCandidatesForElection(result, false));
        }
        return result;
    }

    @Override
    public List<Election> getFinishedParliamentRawElections() {
        return electionRepository.getParliamentRawElectionsBefore(OffsetDateTime.now().minusHours(14));
    }

    @Override
    public List<Election> getFinishedPresidentRawElections() {
        return electionRepository.getPresidentRawElectionsBefore(OffsetDateTime.now().minusHours(14));
    }

    @Override
    public List<Election> getFinishedAllRawElections() {
        return electionRepository.getAllRawElectionsBefore(OffsetDateTime.now().minusHours(14));
    }

    @Override
    public ElectionResult getElectionResultFromFinished(Long electionId) {
        if (electionId != null) {
            List<Election> elections = getFinishedAllRawElections();
            if (elections != null) {
                for (Election election : elections) {
                    if (election.getId().equals(electionId)) {
                        List<ResultItem> resultItems = candidateRepository.getResultItemsForElection(election, false);
                        int totalBallots = electionRepository.getTotalBallotsForElection(election);
                        return new ElectionResult(election, totalBallots, resultItems);
                    }
                }
            }
        }
        return null;
    }


}
