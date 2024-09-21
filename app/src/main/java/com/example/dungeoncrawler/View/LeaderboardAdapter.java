package com.example.dungeoncrawler.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dungeoncrawler.Model.LeaderboardEntry;
import com.example.dungeoncrawler.R;


import java.util.List;


public class LeaderboardAdapter extends
        RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {
    private List<LeaderboardEntry> entries;

    /**
     * Leaderboard Adaptor.
     * @param entries list of entires
     */
    public LeaderboardAdapter(List<LeaderboardEntry> entries) {
        this.entries = entries;
    }

    @Override
    public LeaderboardViewHolder
        onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.leaderboard_entry, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LeaderboardViewHolder holder, int position) {
        LeaderboardEntry entry = entries.get(position);
        holder.playerNameTextView.setText(entry.getPlayerName());
        holder.playerScoreTextView.setText("Score: " + entry.getScore());
        holder.playerDateandTime.setText(entry.getDateTime());
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public static class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        private TextView playerDateandTime;
        private TextView playerNameTextView;
        private TextView playerScoreTextView;

        public LeaderboardViewHolder(View itemView) {
            super(itemView);
            playerNameTextView = itemView.findViewById(R.id.playerName);
            playerScoreTextView = itemView.findViewById(R.id.playerScore);
            playerDateandTime = itemView.findViewById(R.id.playerDateandTime);
        }
    }
}
