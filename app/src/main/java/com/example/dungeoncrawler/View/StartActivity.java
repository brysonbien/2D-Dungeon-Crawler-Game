package com.example.dungeoncrawler.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dungeoncrawler.R;

public class StartActivity extends AppCompatActivity {
    private ImageButton startGame;
    private ImageButton quitGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startGame = (ImageButton) findViewById(R.id.start_game_btn);
        quitGame = (ImageButton) findViewById(R.id.quit_game_btn);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
        quitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeMainActivity();
            }
        });
    }

    /**
     * Opens main activity
     */
    public void openMainActivity() {
        Intent intent = new Intent(StartActivity.this, ConfigActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Closes main activity
     */
    public void closeMainActivity() {
        Intent intent = new Intent(StartActivity.this, EndActivityQuit.class);
        startActivity(intent);
        finish();
    }
}