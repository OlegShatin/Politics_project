package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Election;

import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface CandidateRepository {
    List<Candidate> getCandidatesForElection(Election election, boolean includeDefaultCandidates);
}
