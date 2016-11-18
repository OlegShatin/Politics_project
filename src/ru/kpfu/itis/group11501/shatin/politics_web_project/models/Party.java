package ru.kpfu.itis.group11501.shatin.politics_web_project.models;

import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class Party {
    public Long getPartyId() {
        return partyId;
    }

    public int getSeatsInParliament() {
        return seatsInParliament;
    }

    public List<Supporter> getSupporters() {
        return supporters;
    }

    private Long partyId;
    private int seatsInParliament;
    private List<Supporter> supporters;

    public Party(Long partyId, int seatsInParliament, List<Supporter> supporters) {

        this.partyId = partyId;
        this.seatsInParliament = seatsInParliament;
        this.supporters = supporters;
    }
}
