package ru.kpfu.itis.group11501.shatin.politics_web_project.services;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Message;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

/**
 * @author Oleg Shatin
 *         11-501
 */
public interface UserService {
    boolean userExists(String email, String hashed_password);

    User getUser(String email);
    User authorize(Object sessionUserParam);


    boolean emailAlreadyExists(String email);

    boolean samePassportExists(String passportSeries, String passportNum);

    boolean passportDataIsValid(String passportSeries, String passportNum);

    User createNewUser(String password, String email, Role role, int timezoneOffset, String passportSeries, String passportNum, String name, String surname, String patronymic, LocalDate birthdayDate);

    User getUser(Long userId);

    User getGuest();

    Message addMessage(User sender, Long recipientId, String messageText);

    List<Candidate> getUnmessagedCandidatesByUser(User user, List<Candidate> candidates);

    SortedMap<Message, Candidate> getPageOfConversationsWithCandidatesForUser(User user, int page);

    int getMaxPageOfListOfConversationsForUser(User user);

    SortedMap<Message,User> getPageOfConversationsWithUsersForUser(User user, int page);


    List<Message> getConversationWithUserForUser(User user, Long otherUserId);

    boolean updateEmail(long userId, String email);

    boolean updatePassword(long userId, String hashedString);

    boolean emailIsValid(String email);
}
