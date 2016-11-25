package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Election;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.ResultItem;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface CandidateRepository {
    List<Candidate> getCandidatesForElection(Election election, boolean includeDefaultCandidates);

    Candidate getCandidateForAgent(User agent);

    Candidate getCandidateById(Long candidateId);

    boolean updateName(long id, String name);

    boolean updateId(long id, long newId);

    boolean updateInfo(long id, String info);

    boolean updateAchievements(long id, String achievements);

    boolean updateElectionProgram(long id, String program);

    boolean updateImageSrc(long id, String src);

    boolean updateSeatsInParliament(long id, int seats);

    List<ResultItem> getResultItemsForElection(Election election, boolean includeDefaultCandidates);
}
