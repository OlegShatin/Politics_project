package ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Message;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.CandidateRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.MessageRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.UserRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls.CandidateRepositoryImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls.MessageRepositoryImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls.UserRepositoryImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserService;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private final Pattern PASS_SERIES_PATTERN;
    private final Pattern PASS_NUMBER_PATTERN;
    private final Pattern EMAIL_PATTERN;
    private MessageRepository messageRepository;
    private CandidateRepository candidateRepository;

    public UserServiceImpl() {
        userRepository = new UserRepositoryImpl();
        messageRepository = new MessageRepositoryImpl();
        candidateRepository = new CandidateRepositoryImpl();
        PASS_SERIES_PATTERN
                = Pattern.compile("(^[0-9]{4}$)");
        PASS_NUMBER_PATTERN = Pattern.compile("(^[0-9]{6}$)");
        EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean userExists(String email, String password) {
        return userRepository.userExists(email, Helper.getHashedString(password));
    }

    @Override
    public User getUser(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public User authorize(Object sessionUserParam) {
        return sessionUserParam != null ?
                (User)sessionUserParam : getGuest();
    }

    @Override
    public boolean emailAlreadyExists(String email) {
        return userRepository.containsThisEmail(email);
    }

    @Override
    public boolean samePassportExists(String passportSeries, String passportNum) {
        return userRepository.samePassportExists(passportSeries, passportNum);
    }

    @Override
    public boolean passportDataIsValid(String passportSeries, String passportNum) {
        return PASS_SERIES_PATTERN.matcher(passportSeries).matches() && PASS_NUMBER_PATTERN.matcher(passportNum).matches();
    }

    @Override
    public User createNewUser(String password, String email, Role role, int timezoneOffset, String passportSeries, String passportNum, String name, String surname, String patronymic, LocalDate birthdayDate) {
        if (userRepository.addNewUser(password, email, role, timezoneOffset, passportSeries, passportNum, name, surname, patronymic, birthdayDate)) {
            return userRepository.getUserByEmail(email);
        } else return null;
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public User getGuest() {
        //// TODO: 09.11.2016 fix this hardcode: required time offset session attribute
        return new User(Role.GUEST, OffsetDateTime.now().getOffset());
    }

    @Override
    public Message addMessage(User sender, Long recipientId, String messageText) {
        Message newMessage = new Message(
                sender.getId(),
                recipientId,
                messageText,
                OffsetDateTime.now(sender.getTimezoneOffset())
        );
        return messageRepository.addMessage(newMessage, sender);
    }

    @Override
    public List<Candidate> getUnmessagedCandidatesByUser(User user, List<Candidate> candidates) {
        List<Candidate> result = new ArrayList<>();
        for (Candidate candidate : candidates) {
            if (!messageRepository.existsConversation(user, userRepository.getUserById(candidate.getAgentId()))) {
                result.add(candidate);
            }
        }
        return result;
    }

    @Override
    public SortedMap<Message, Candidate> getPageOfConversationsWithCandidatesForUser(User user, int page) {
        SortedMap<Message, Candidate> conversations = new TreeMap<Message, Candidate>();
        try {
            for (Map.Entry<Message, User> entry : messageRepository.getLastMessagesWithOffsetForUser(user, (page - 1) * 10, 10).entrySet()) {
                if (entry.getValue() != null) {
                    Candidate candidate = candidateRepository.getCandidateForAgent(entry.getValue());
                    if (candidate != null) {
                        conversations.put(entry.getKey(), candidate);
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return conversations;
    }

    @Override
    public int getMaxPageOfListOfConversationsForUser(User user) {
        int result = messageRepository.getCountOfRowsFromLastMessagesTableForUser(user);
        if (result % 10 != 0)
            result = result / 10 + 1;
        else
            result = result / 10;
        if (result == 0)
            result = 1;
        return result;
    }

    @Override
    public SortedMap<Message, User> getPageOfConversationsWithUsersForUser(User user, int page) {
        SortedMap<Message, User> conversations = new TreeMap<Message, User>();
        try {

            for (Map.Entry<Message, User> entry : messageRepository.getLastMessagesWithOffsetForUser(user, (page - 1) * 10, 10).entrySet()) {
                if (entry.getValue() != null) {
                    User voter = entry.getValue();
                    if (voter != null) {
                        conversations.put(entry.getKey(), voter);
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return conversations;
    }


    @Override
    public List<Message> getConversationWithUserForUser(User user, Long otherUserId) {
        return messageRepository.getMessagesBetweenUsersSortedByTimeDescForUser(user, getUser(otherUserId));
    }

    @Override
    public boolean updateEmail(long userId, String email) {
        return userRepository.updateEmail(userId, email);
    }

    @Override
    public boolean updatePassword(long userId, String hashedString) {
        return userRepository.updatePassword(userId, hashedString);
    }

    @Override
    public boolean emailIsValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
