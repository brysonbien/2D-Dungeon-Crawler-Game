package com.example.dungeoncrawler.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dungeoncrawler.Model.GameSession;
import com.example.dungeoncrawler.Model.LeaderboardEntry;
import com.example.dungeoncrawler.Model.LeaderboardModel;
import com.example.dungeoncrawler.R;
import com.example.dungeoncrawler.ViewModel.GameSessionViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EndActivity extends AppCompatActivity {
    private Button restartButton;

    private RecyclerView recyclerView;
    private LeaderboardAdapter adapter;
    private Bundle winData;
    private LinearLayout mostRecentView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        mediaPlayer = MediaPlayer.create(this, R.raw.victory_music);
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();

        Date currentDateAndTime = new Date();
        SimpleDateFormat dateFormat = new
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedDateAndTime =
                dateFormat.format(currentDateAndTime);

        GameSessionViewModel gameSessionViewModel = GameSessionViewModel.getInstance();

        LeaderboardEntry leaderboardEntry = new
                LeaderboardEntry(gameSessionViewModel.getInputName(),
                GameSession.getInstance().getCurrentScore(), formattedDateAndTime);
        LeaderboardModel.getInstance().addLeaderboardEntry(leaderboardEntry);

        mostRecentView = findViewById(R.id.leaderboardContainer);

        LayoutInflater inflater = getLayoutInflater();
        View leaderboardEntryView =
                inflater.inflate(R.layout.leaderboard_entry, mostRecentView, false);
        mostRecentView.addView(leaderboardEntryView);

        TextView nameTextView = leaderboardEntryView.findViewById(R.id.playerName);
        TextView timeTextView = leaderboardEntryView.findViewById(R.id.playerDateandTime);
        TextView scoreTextView = leaderboardEntryView.findViewById(R.id.playerScore);

        nameTextView.setText(gameSessionViewModel.getInputName());
        timeTextView.setText(formattedDateAndTime);
        scoreTextView.setText(Double.toString(GameSession.getInstance().getCurrentScore()));

        recyclerView = findViewById(R.id.leaderboardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<LeaderboardEntry> topEntries = gameSessionViewModel.leaderboardSetup();
        Log.d("Leaderboard", Double.toString(topEntries.get(0).getScore()));
        adapter = new LeaderboardAdapter(topEntries);
        recyclerView.setAdapter(adapter);

        restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(view -> {
            GameSession.getInstance().resetGame();
            startActivity(new Intent(EndActivity.this, StartActivity.class));
        });
    }
}