package ru.kpfu.itis.group11501.shatin.politics_web_project.models;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class Candidate {
    private Long id;
    private String name;
    private String info;
    private String achievements;
    private String electionProgram;
    private String imageSrc;
    private Long agentId;
    private Party party;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getAchievements() {
        return achievements;
    }

    public String getElectionProgram() {
        return electionProgram;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public Long getAgentId() {
        return agentId;
    }

    public Party getParty() {
        return party;
    }

    public Candidate(Long id, String name, String info, String achievements, String electionProgram, String imageSrc, Long agentId, Party party) {

        this.id = id;
        this.name = name;
        this.info = info;
        this.achievements = achievements;
        this.electionProgram = electionProgram;
        this.imageSrc = imageSrc;
        this.agentId = agentId;
        this.party = party;
    }
}
