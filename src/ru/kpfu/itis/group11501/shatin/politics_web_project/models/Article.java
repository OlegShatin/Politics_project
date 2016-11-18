package ru.kpfu.itis.group11501.shatin.politics_web_project.models;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class Article {
    private long id;
    private String headline;
    private String content;
    private OffsetDateTime publicationDateTime;

    public long getId() {
        return id;
    }

    public String getHeadline() {
        return headline;
    }

    public String getContent() {
        return content;
    }

    public OffsetDateTime getPublicationDateTime() {
        return publicationDateTime;
    }

    public Article(long id, String headline, String content, OffsetDateTime publicationDateTime) {
        this.id = id;
        this.headline = headline;
        this.content = content;
        this.publicationDateTime = publicationDateTime;
    }
}
