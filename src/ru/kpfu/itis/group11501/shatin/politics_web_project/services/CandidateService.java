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
    boolean updateName(long id, String name);
    boolean updateId(long id, String newId);
    boolean updateInfo(long id, String info);
    boolean updateAchievements(long id, String achievements);
    boolean updateElection_program(long id, String program);
    boolean updateImage_src(long id, String src);
    boolean updateSeats_in_parliament(long id, String seats);
}
