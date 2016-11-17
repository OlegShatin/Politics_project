package ru.kpfu.itis.group11501.shatin.politics_web_project.models;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class Supporter {
    private Long id;
    private String name;
    private String surname;
    private String imageSrc;
    private long partyId;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public long getPartyId() {
        return partyId;
    }

    public Supporter(Long id, String name, String surname, String imageSrc, long partyId) {

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.imageSrc = imageSrc;
        this.partyId = partyId;
    }
}
