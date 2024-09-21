package com.example.dungeoncrawler.Model;

public class LeaderboardEntry {
    private String playerName;
    private double score;
    private String dateTime;

    /**
     * Leaderboard entry.
     * @param playerName string
     * @param score double
     * @param dateTime string
     */
    public LeaderboardEntry(String playerName, double score, String dateTime) {
        this.playerName = playerName;
        this.score = score;
        this.dateTime = dateTime;
    }

    /**
     * Get player name.
     * @return player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Get score.
     * @return score
     */
    public double getScore() {
        return score;
    }

    /**
     * Get date time.
     * @return date time
     */
    public String getDateTime() {
        return dateTime;
    }
}
