package ru.kpfu.itis.group11501.shatin.politics_web_project.models;

import java.time.Clock;
import java.time.ZoneOffset;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class User {
    private String hashedPassword;
    private String email;
    private long id;
    private Role role;
    private ZoneOffset timezoneOffset;

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
