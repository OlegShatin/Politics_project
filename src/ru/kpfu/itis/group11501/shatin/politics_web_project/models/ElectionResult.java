package ru.kpfu.itis.group11501.shatin.politics_web_project.models;

import java.util.List;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class ElectionResult {
    private Election election;
    private int totalBallots;
    private int totalVotes;
    private List<ResultItem> ballotItems;

    public ElectionResult(Election election, int totalBallots, List<ResultItem> ballotItems) {
        this.election = election;
        this.totalBallots = totalBallots;
        this.totalVotes = 0;
        if (ballotItems != null && !ballotItems.isEmpty()){
            for (ResultItem item:ballotItems){
                this.totalVotes+= item.getVotes();
            }
        }
        this.ballotItems = ballotItems;
    }

    public Election getElection() {
        return election;
    }

    public int getTotalBallots() {
        return totalBallots;
    }

    public int getSpoiledBallots() {
        return totalBallots - totalVotes;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public List<ResultItem> getBallotItems() {
        return ballotItems;
    }
}

