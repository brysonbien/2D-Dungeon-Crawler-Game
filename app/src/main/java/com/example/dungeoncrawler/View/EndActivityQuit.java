package com.example.dungeoncrawler.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import com.example.dungeoncrawler.Model.GameSession;
import com.example.dungeoncrawler.R;

public class EndActivityQuit extends AppCompatActivity {
    private Button restartButton;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end3);

        mediaPlayer = MediaPlayer.create(this, R.raw.game_over);
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.start();

        restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(view -> {
            GameSession.getInstance().resetGame();
            startActivity(new Intent(EndActivityQuit.this, StartActivity.class));
        });
    }
}