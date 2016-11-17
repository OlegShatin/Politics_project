package ru.kpfu.itis.group11501.shatin.politics_web_project.models;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class Election {

    private Long id;
    private List<Candidate> candidates;
    private OffsetDateTime finishTime;
    private ElectionType type;
    private OffsetDateTime startTime;

    public Election(Long id, ElectionType type, OffsetDateTime startTime, OffsetDateTime finishTime, List<Candidate> candidates) {
        this.id = id;
        this.type = type;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.candidates = candidates;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public OffsetDateTime getFinishTime() {
        return finishTime;
    }

    public ElectionType getType() {
        return type;
    }

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public Long getId() {
        return id;
    }
}
