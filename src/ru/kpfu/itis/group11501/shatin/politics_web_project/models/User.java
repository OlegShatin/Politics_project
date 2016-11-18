package ru.kpfu.itis.group11501.shatin.politics_web_project.models;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class User {
    private String hashedPassword;
    private String email;
    private long id;
    private LocalDate birthday;
    private String name;
    private String surname;
    private String patronymic;
    private Role role;
    private ZoneOffset timezoneOffset;

    public User(String hashedPassword, String email, long id, Role role, ZoneOffset timezoneOffset, LocalDate birthday, String name, String surname, String patronymic) {
        this(role, timezoneOffset);
        this.hashedPassword = hashedPassword;
        this.email = email;
        this.id = id;
        this.birthday = birthday;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    public User(Role role, ZoneOffset offset) {
        this.role = role;
        timezoneOffset = offset;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getEmail() {
        return email;
    }

    public long getID() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public ZoneOffset getTimezoneOffset() {
        return timezoneOffset;
    }

}
