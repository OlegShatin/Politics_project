package ru.kpfu.itis.group11501.shatin.politics_web_project.services;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Election;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface CandidateService {
    Candidate getCandidateForAgent(User agent);

    Candidate getCandidateFromElectionById(Election election, Long candidateId);

    Candidate getCandidateById(Long id);
}
