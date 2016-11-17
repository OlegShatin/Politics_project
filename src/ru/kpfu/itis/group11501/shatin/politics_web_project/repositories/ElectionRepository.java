package ru.kpfu.itis.group11501.shatin.politics_web_project.repositories;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Election;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface ElectionRepository {
    Election getCurrentRawElectionForUser(User user);

    boolean userVotedOnElection(User user, Election election);
}
