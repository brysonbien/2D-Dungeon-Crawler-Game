package com.example.dungeoncrawler.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardModel {
    private static LeaderboardModel leaderboard;
    private List<LeaderboardEntry> leaderboardEntries;

    /**
     * Leader board model.
     */
    private LeaderboardModel() {
        leaderboardEntries = new ArrayList<>();
    }

    /**
     * Leader board model instance.
     * @return leader board
     */
    public static LeaderboardModel getInstance() {
        if (leaderboard == null) {
            leaderboard = new LeaderboardModel();
        }
        return leaderboard;
    }

    /**
     * List leader board entry.
     * @return leader board entries
     */
    public List<LeaderboardEntry> getLeaderboardEntries() {
        return leaderboardEntries;
    }

    /**
     * Add leader board entry.
     * @param entry leader board entry
     */
    public void addLeaderboardEntry(LeaderboardEntry entry) {
        leaderboardEntries.add(entry);
        Collections.sort(leaderboardEntries,
                (entry1, entry2) -> Double.compare(entry2.getScore(), entry1.getScore()));
        // Log the size of the entries list
        Log.d("Leaderboard",
                "Number of entries: " + leaderboardEntries.size());
    }
}