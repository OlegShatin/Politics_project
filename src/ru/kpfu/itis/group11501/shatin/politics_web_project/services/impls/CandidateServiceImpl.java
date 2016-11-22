package ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Election;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.CandidateRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls.CandidateRepositoryImpl;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class CandidateServiceImpl implements ru.kpfu.itis.group11501.shatin.politics_web_project.services.CandidateService {
    CandidateRepository candidateRepository;
    public CandidateServiceImpl(){
        candidateRepository = new CandidateRepositoryImpl();
    }
    @Override
    public Candidate getCandidateForAgent(User agent) {
        return candidateRepository.getCandidateForAgent(agent);
    }

    @Override
    public Candidate getCandidateFromElectionById(Election election, Long candidateId) {
        for (Candidate candidate : election.getCandidates()){
            if (candidate.getId().equals(candidateId)){
                return candidate;
            }
        }
        return null;
    }

}
