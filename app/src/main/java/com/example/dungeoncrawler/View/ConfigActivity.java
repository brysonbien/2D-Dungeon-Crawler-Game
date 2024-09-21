package com.example.dungeoncrawler.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dungeoncrawler.ViewModel.ConfigViewModel;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.media.MediaPlayer;

import com.example.dungeoncrawler.R;

public class ConfigActivity extends AppCompatActivity {
    private EditText inputName;
    private Button enterInfo;
    private String name;
    private Bundle gameSettingsInit;
    private TextView emptyNameWarning;
    private TextView longNameWarning;
    private SpriteAnimationView playerSpriteView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        mediaPlayer = MediaPlayer.create(this, R.raw.intro_music);
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        // initializing objects for text on screen
        inputName = findViewById(R.id.inputName);
        enterInfo = findViewById(R.id.enterInfo);
        emptyNameWarning = findViewById(R.id.emptyNameWarning);
        emptyNameWarning.setVisibility(View.INVISIBLE);
        longNameWarning = findViewById(R.id.longNameWarning);
        longNameWarning.setVisibility(View.INVISIBLE);
        //preparing a bundle to send to the main activity
        gameSettingsInit = new Bundle();
        enterInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] info;
                info = ConfigViewModel.handleConfigSelection(findViewById(R.id.difficultyRadioGroup), findViewById(R.id.characterRadioGroup));
                sendData(info[0], info[1], info[2]);
            }
        });
        playerSpriteView = findViewById(R.id.playerSpriteView);
        playerSpriteView.setSpriteSheet(R.drawable.player_pumpkin, 9);
        //Start the animation
        playerSpriteView.invalidate(); // Trigger initial draw
    }

    /**
     * Send data about name, difficulty, and starting hp
     */
    public void sendData(int difficulty, int startingHP, int characterNum) {
        name = inputName.getText().toString().trim();
        if (name.isEmpty()) {
            flashEmptyNameWarning();
        } else if (name.length() > 20) {
            flashLongNameWarning();
        } else {
            Intent intent = new Intent(ConfigActivity.this, MainActivity.class);
            gameSettingsInit.putString("NAME", name);
            gameSettingsInit.putDouble("DIFFICULTY", difficulty);
            gameSettingsInit.putInt("STARTING_HP", startingHP);
            gameSettingsInit.putInt("CHARACTER_NUM", characterNum);
            intent.putExtras(gameSettingsInit);
            mediaPlayer.release();
            startActivity(intent);
            finish();
        }
    }

    /**
     * Flash empty name warning.
     */
    public void flashEmptyNameWarning() {
        emptyNameWarning.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                emptyNameWarning.setVisibility(View.INVISIBLE);
            }
        }, 10000);
    }

    /**
     * Flash long name warning.
     */
    public void flashLongNameWarning() {
        longNameWarning.setVisibility(View.VISIBLE);
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputName.getWindowToken(), 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                longNameWarning.setVisibility(View.INVISIBLE);
            }
        }, 10000);
    }

    /**
     * Is name invalid
     * @param name name
     * @return boolean
     */
    public static boolean isNameInvalid(String name) {
        return name == null || name.trim().isEmpty();
    }
}