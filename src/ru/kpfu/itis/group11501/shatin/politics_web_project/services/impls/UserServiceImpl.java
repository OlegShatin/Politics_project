package ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls;

import ru.kpfu.itis.group11501.shatin.politics_web_project.helpers.Helper;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Message;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Role;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.MessageRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.UserRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls.MessageRepositoryImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls.UserRepositoryImpl;
import ru.kpfu.itis.group11501.shatin.politics_web_project.services.UserService;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.regex.Pattern;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private final Pattern PASS_SERIES_PATTERN;
    private final Pattern PASS_NUMBER_PATTERN;
    private MessageRepository messageRepository;

    public UserServiceImpl(){
        userRepository = new UserRepositoryImpl();
        messageRepository = new MessageRepositoryImpl();
        PASS_SERIES_PATTERN
            = Pattern.compile("(^[0-9]{4}$)");
        PASS_NUMBER_PATTERN = Pattern.compile("(^[0-9]{6}$)");
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
        if (userRepository.addNewUser(password, email, role, timezoneOffset, passportSeries, passportNum, name, surname, patronymic, birthdayDate)){
            return userRepository.getUserByEmail(email);
        } else return null;
    }

    @Override
    public User getUser(long userId) {
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
                sender.getID(),
                recipientId,
                messageText,
                OffsetDateTime.now(sender.getTimezoneOffset())
        );
        return messageRepository.addMessage(newMessage);
    }
}
