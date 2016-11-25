package ru.kpfu.itis.group11501.shatin.politics_web_project.models;

public class ResultItem {
    private Candidate candidate;
    private int votes;

    public Candidate getCandidate() {
        return candidate;
    }

    public int getVotes() {
        return votes;
    }

    public ResultItem(Candidate candidate, int votes) {
        this.candidate = candidate;
        this.votes = votes;
    }
}
