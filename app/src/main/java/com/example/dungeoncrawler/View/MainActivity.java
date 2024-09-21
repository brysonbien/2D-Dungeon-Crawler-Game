package com.example.dungeoncrawler.View;

import static com.example.dungeoncrawler.Model.MapLayout.NUMBER_OF_ROW_TILES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dungeoncrawler.Model.CollectablePowerup;
import com.example.dungeoncrawler.Model.DemonEnemy;
import com.example.dungeoncrawler.Model.DemonFactory;
import com.example.dungeoncrawler.Model.Enemy;
import com.example.dungeoncrawler.Model.EnemyFactory;
import com.example.dungeoncrawler.Model.HeartDecorator;
import com.example.dungeoncrawler.Model.MoveDown;
import com.example.dungeoncrawler.Model.MoveLeft;
import com.example.dungeoncrawler.Model.MoveRight;
import com.example.dungeoncrawler.Model.MoveUp;
import com.example.dungeoncrawler.Model.OgreEnemy;
import com.example.dungeoncrawler.Model.OgreFactory;
import com.example.dungeoncrawler.Model.OrcFactory;
import com.example.dungeoncrawler.Model.Player;
import com.example.dungeoncrawler.Model.Powerup;
import com.example.dungeoncrawler.Model.PowerupsDecorator;
import com.example.dungeoncrawler.Model.SpriteSheet;
import com.example.dungeoncrawler.Model.TileMap;
import com.example.dungeoncrawler.Model.ZombieEnemy;
import com.example.dungeoncrawler.Model.ZombieFactory;
import com.example.dungeoncrawler.ViewModel.GameSessionViewModel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

import android.media.MediaPlayer;
import android.widget.ToggleButton;

import com.example.dungeoncrawler.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TileMap tileMap;
    private TextView userName;
    private TextView healthPoints;
    private TextView difficultyMode;
    private ImageView playerImageView;
    private String difficultyText;
    private double currentScore;
    private Handler scoreHandler;
    private Handler playerMovementHandler;
    private Runnable scoreRunnable;
    private Runnable playerMovementRunnable;
    private TextView scoreText;
    private TextView posText;
    private int currentScreen = 1;
    private ConstraintLayout constraintLayout;
    private Player player;
    private ImageView tileImageView;
    private float tileMapScale = 3;
    private Bitmap tileBitmap;
    private GameSessionViewModel gameSessionViewModel;
    private int mapIndex = 0;
    private TileMapView tileMapView;
    private Canvas mapCanvas;
    private ImageView enemyImageView1;
    private ImageView enemyImageView2;

    private Enemy enemy1;
    private Enemy enemy2;
    private EnemyFactory enemyFactory1;
    private EnemyFactory enemyFactory2;
    private ImageView powerupImageView1;
    private ImageView powerupImageView2;
    private ImageView weaponDisplay;
    private ImageView weaponDisplay2;

    private Runnable playerFightRunnable;
    private Handler playerFightHandler;

    private boolean isGameOver = false;
    
    private boolean killed;

    private MediaPlayer mediaPlayer;
    private MediaPlayer footstepPlayerLeftRight;
    private MediaPlayer footstepPlayerUp;
    private MediaPlayer footstepPlayerDown;
    private MediaPlayer damageDone;
    private int currentKeyCode;
    private int currentHealth;
    private int initialHealth;
    private int previousHealth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentKeyCode = 0;
        killed = false;
        player = Player.getInstance();
        if (player.getHp() <= 0) {
            player.setHp(previousHealth);
        } else {
            previousHealth = player.getHp();
        }
        initialHealth = previousHealth;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
        mediaPlayer.setVolume(0.5f, 0.5f);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        footstepPlayerLeftRight = MediaPlayer.create(this, R.raw.footstep_leftright);
        footstepPlayerUp = MediaPlayer.create(this, R.raw.footstep_up);
        footstepPlayerDown = MediaPlayer.create(this, R.raw.footstep_down);
        damageDone = MediaPlayer.create(this, R.raw.damage_sound);
        damageDone.setVolume(1.0f, 1.0f);
        ToggleButton toggleButtonMute = findViewById(R.id.toggleButtonMute);

        /**
         * Create toggle button for muting/unmuting background music.
         */
        toggleButtonMute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mediaPlayer.setVolume(0, 0); }
                else {
                    mediaPlayer.setVolume(0.5f, 0.5f);
                }
            }
        });

        Intent intent = getIntent();
        gameSessionViewModel = GameSessionViewModel.getInstance();
        gameSessionViewModel.extractConfigInfo(intent);
        setScreenDims(gameSessionViewModel);
        tileMap = new TileMap(new SpriteSheet(this), mapIndex);
        renderTileMap(tileMap);
        findMainActivityViewsbyId();
        initializePlayerPos(player);
        userName.setText(gameSessionViewModel.getInputName());
        difficultyText = getDifficultyText(gameSessionViewModel.getDifficulty());
        updateHPDisplay();
        difficultyMode.setText("Mode: " + difficultyText);
        displayCharacterChoice(gameSessionViewModel.getCharacterNum());
        displayPowerups();
        displayEnemies();
        scoreHandler = new Handler();
        scoreRunnable = new Runnable() {
            @Override
            public void run() {
                currentScore -= 1;
                currentHealth = player.getHp();
                if (currentHealth < initialHealth) {
                    currentScore -= (initialHealth - currentHealth);
                    initialHealth = currentHealth;
                }
                currentScore = Math.max(currentScore, 0);
                displayScore();
                scoreHandler.postDelayed(this, 3000);
            }
        };
        currentScore = GameSessionViewModel.getInstance().updateScore(
                scoreHandler, scoreRunnable);

        playerMovementHandler = new Handler();
        playerMovementRunnable = new Runnable() {
            @Override
            public void run() {
                if (!killed) {
                    movementUpdate(currentKeyCode);
                    playerMovementHandler.post(this);
                }
            }
        };

        playerFightHandler = new Handler();
        playerFightRunnable = new Runnable() {
            @Override
            public void run() {
                if (!killed) {
                    if (player.getIsArmed()) {
                        attack();
                    }
                    playerFightHandler.post(this);
                }
            }
        };

        Handler enemyHandler = new Handler();
        Runnable enemyRunnable = new Runnable() {
            @Override
            public void run() {
                if (player.checkEnemyCollisions(enemy1) || player.checkEnemyCollisions(enemy2)) {
                    damageDone.start();
                }
                if (!killed) {
                    checkCollisions(enemy1, enemy2);
                    player.checkEnemyCollisions(enemy1);
                    player.checkEnemyCollisions(enemy2);
                    if (enemy1.getIsDead()) {
                        Log.d("die", "hidden");
                        enemyImageView1.setVisibility(View.GONE);
                    }
                    if (enemy2.getIsDead()) {
                        Log.d("die", "hidden");
                        enemyImageView2.setVisibility(View.GONE);
                    }
                    playerImageView.setX(player.getX());
                    playerImageView.setY(player.getY());
                    enemyImageView1.setX(enemy1.getX());
                    enemyImageView2.setX(enemy2.getX());
                    enemyImageView1.setY(enemy1.getY());
                    enemyImageView2.setY(enemy2.getY());
                    enemyHandler.postDelayed(this, 50);
                }
            }
        };
        constraintLayout.requestFocus();
        scoreHandler.post(scoreRunnable);
        enemyHandler.post(enemyRunnable);
        playerFightHandler.post(playerFightRunnable);
        playerMovementHandler.post(playerMovementRunnable);
    }

    /**
     * Update HP Display.
     */
    private void updateHPDisplay() {
        healthPoints.setText("HP: " + player.getHp());
    }

    /**
     * Check collisions.
     * @param enemy1 enemy
     * @param enemy2 enemy
     */
    private void checkCollisions(Enemy enemy1, Enemy enemy2) {
        if (mapIndex == 0) {
            // orc
            if (enemy1.getX() < 110) {
                enemy1.setDirection(1);
            } else if (enemy1.getX() > 925) {
                enemy1.setDirection(-1);
            }
            // ogre
            Log.d("ogre", "x: " + enemy2.getX() + " y: " + enemy2.getY());
            if (enemy2.getDirection() == 1 && enemy2.getX() > 910) {
                enemy2.setDirection(0);
                enemy2.setYDirection(-1);
            } else if (enemy2.getYDirection() == -1 && enemy2.getY() < 750) {
                enemy2.setDirection(-1);
                enemy2.setYDirection(0);
            } else if (enemy2.getDirection() == -1 && enemy2.getX() < 595) {
                enemy2.setDirection(0);
                enemy2.setYDirection(1);
            } else if (enemy2.getYDirection() == 1 && enemy2.getY() > 1000) {
                enemy2.setDirection(1);
                enemy2.setYDirection(0);
            }
        } else if (mapIndex == 1) {
            if (enemy1.getDirection() == 1 && enemy1.getX() > 500) {
                enemy1.setDirection(0);
                enemy1.setYDirection(-1);
            } else if (enemy1.getYDirection() == -1 && enemy1.getY() < 400) {
                enemy1.setDirection(-1);
                enemy1.setYDirection(0);
            } else if (enemy1.getDirection() == -1 && enemy1.getX() < 300) {
                enemy1.setDirection(0);
                enemy1.setYDirection(1);
            } else if (enemy1.getYDirection() == 1 && enemy1.getY() > 500) {
                enemy1.setDirection(1);
                enemy1.setYDirection(0);
            }
            if (enemy2.getY() < 715) {
                enemy2.setDirection(1);
            } else if (enemy2.getY() > 1480) {
                enemy2.setDirection(-1);
            }
        } else {
            if (enemy1.getY() < 410) {
                enemy1.setDirection(1);
            } else if (enemy1.getY() > 1480) {
                enemy1.setDirection(-1);
            }
            if (enemy2.getX() > 900) {
                enemy2.setDirection(-1);
                enemy2.setYDirection(-1);
            } else if (enemy2.getY() < 500) {
                enemy2.setDirection(1);
                enemy2.setYDirection(1);
            }
        }
        enemy1.move();
        enemy2.move();
        Log.d("ogredir", "dir" + enemy2.getYDirection());
    }

    /**
     * Render Tile Map.
     * @param tileMap tile map
     */
    private void renderTileMap(TileMap tileMap) {
        tileMapView = new TileMapView(this, tileMap);
        mapCanvas = new Canvas();
        tileMap.draw(mapCanvas);
        tileImageView = findViewById(R.id.tileMap);
        tileBitmap = tileMap.getMapBitmap();
        tileImageView.setImageBitmap(tileBitmap);
        tileImageView.setScaleX(tileMapScale);
        tileImageView.setScaleY(tileMapScale);
        gameSessionViewModel.setTileMapScale(tileMapScale);
        tileImageView.getDrawable().getBounds();
        ViewTreeObserver viewTreeObserver = tileImageView.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                gameSessionViewModel.setTileMapX(((gameSessionViewModel.getScreenWidth() / 2)
                        - ((tileMapScale * tileImageView.getWidth()) / 2) + 18));
                gameSessionViewModel.setTileMapY(((gameSessionViewModel.getScreenHeight() / 2)
                        - ((tileMapScale * tileImageView.getHeight()) / 2)) + 30);
                tileImageView.getViewTreeObserver().removeOnPreDrawListener(this);

                return true;
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        gameSessionViewModel.setTileImageDimInfo(tileBitmap.getHeight() / NUMBER_OF_ROW_TILES);
    }

    /**
     * Find main activity views by id.
     */
    private void findMainActivityViewsbyId() {
        userName = findViewById(R.id.userName);
        healthPoints = findViewById(R.id.healthPoints);
        playerImageView = findViewById(R.id.characterDisplay);
        difficultyMode = findViewById(R.id.difficultyMode);
        constraintLayout = findViewById(R.id.constraintLayout);
        scoreText = findViewById(R.id.scoreText);
        posText = findViewById(R.id.posText);
        enemyImageView1 = findViewById(R.id.enemyDisplay1);
        enemyImageView2 = findViewById(R.id.enemyDisplay2);
        powerupImageView1 = findViewById(R.id.powerupDisplay1);
        powerupImageView2 = findViewById(R.id.powerupDisplay2);
        weaponDisplay = findViewById(R.id.weapon);
        weaponDisplay2 = findViewById(R.id.weaponView);
    }

    /**
     * Initialize player position.
     * @param player player
     */
    private void initializePlayerPos(Player player) {
        player.setX(150);
        player.setInitX(150);
        player.setY(490);
        player.setInitY(490);
        playerImageView.setX(player.getX());
        playerImageView.setY(player.getY());
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int collectedPowerup = player.checkPowerupCollisions(mapIndex, powerupImageView1.getDrawable(), powerupImageView2.getDrawable());
        handlePowerupCollection(collectedPowerup);
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            currentKeyCode = 19;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            currentKeyCode = 20;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            currentKeyCode = 21;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            currentKeyCode = 22;
        } else if (keyCode == KeyEvent.KEYCODE_SPACE) {
            player.setIsArmed(true);
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        currentKeyCode = -1;
        return true;
    }

    /**
     * Movement Update.
     * @param keyCode
     */
    private void movementUpdate(int keyCode) {
        if (currentKeyCode == 19) {
            player.setMovementStrategy(new MoveUp());
            footstepPlayerUp.start();
        } else if (currentKeyCode == 20) {
            player.setMovementStrategy(new MoveDown());
            footstepPlayerDown.start();
        } else if (currentKeyCode == 21) {
            player.setMovementStrategy(new MoveLeft());
            playerImageView.setScaleX(-player.getWidth());
            footstepPlayerLeftRight.start();
        } else if (currentKeyCode == 22) {
            player.setMovementStrategy(new MoveRight());
            playerImageView.setScaleX(player.getWidth());
            footstepPlayerLeftRight.start();
        }
        if (currentKeyCode > 0) {
            moveDisplay(keyCode);
        }
        Log.d("keycode", "is: " + currentKeyCode);
    }

    /**
     * Attack.
     */
    private void attack() {
        int delay;
        if (player.getHasGoldenSword()) {
            weaponDisplay.setImageResource(R.drawable.weapon_golden_sword);
            delay = 5000;
        } else {
            delay = 800;
        }
        weaponDisplay.setX(player.getX());
        weaponDisplay.setY(player.getY());
        weaponDisplay.setVisibility(View.VISIBLE);
        Handler attackHandler = new Handler();
        attackHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                weaponDisplay.setVisibility(View.GONE);
                player.setIsArmed(false);
            }
        }, delay);
    }

    /**
     * Move Display.
     * @param keyCode int
     */
    private void moveDisplay(int keyCode) {
        player.move();
        if (player.checkEnemyCollisions(enemy1) || player.checkEnemyCollisions(enemy2)) {
            damageDone.start();
        }
        boolean isExit = player.checkCollisions(tileMap, keyCode);
        updateHPDisplay();
        if (isExit) {
            player.setInitX(player.getX());
            player.setInitY(player.getY());
            if (mapIndex == 2) {
                scoreHandler.removeCallbacks(scoreRunnable);
                killed = true;
                mediaPlayer.stop();
                startActivity(new Intent(MainActivity.this, EndActivity.class));
            } else {
                mapIndex += 1;
                tileMap = new TileMap(new SpriteSheet(this), mapIndex);
                displayEnemies();
                displayPowerups();
            }
            renderTileMap(tileMap);
        }
    }

    /**
     * Handle powerup collection.
     * @param collectedPowerup int
     */
    private void handlePowerupCollection(int collectedPowerup) {
        if (player.getHasGoldenSword()) {
            weaponDisplay2.setImageResource(R.drawable.weapon_golden_sword);
        }
        if (collectedPowerup == 1) {
            powerupImageView1.setVisibility(View.GONE);
        }
        if (collectedPowerup == 2) {
            powerupImageView2.setVisibility(View.GONE);
        }
    }

    /**
     * Ends game and loads Game Over screen when health or score hit 0.
     */
    private void gameOver() {
        if (player.getHp() == 0 || currentScore == 0.0) {
            killed = true;
            isGameOver = true;
            scoreHandler.removeCallbacks(scoreRunnable);
            mediaPlayer.stop();
            startActivity(new Intent(MainActivity.this, EndActivityLose.class));
        }
    }

    /**
     * Updates the score and stops when done.
     */
    private void displayScore() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!killed) {
                    gameOver();
                    scoreText.setText("Score: " + currentScore);
                    posText.bringToFront();
                    posText.setText("X: " + player.getX() + " Y: " + player.getY());
                }
            }
        });
    }

    /**
     * Get difficulty text
     * @param difficulty int
     * @return string
     */
    private String getDifficultyText(double difficulty) {
        if (player.getHp() == 50) {
            return "Hard";
        } else if (player.getHp() == 100) {
            return "Normal";
        } else {
            return "Easy";
        }
    }

    /**
     * Display Character Choice.
     * @param characterNum int
     */
    private void displayCharacterChoice(int characterNum) {
        Drawable characterDrawable;
        if (characterNum == 1) {
            characterDrawable =
                    getResources().getDrawable(R.drawable.pumpkin_dude_idle_anim_f0,
                            getApplicationContext().getTheme());
        } else if (characterNum == 2) {
            characterDrawable =
                    getResources().getDrawable(R.drawable.knight_f_idle_anim_f0,
                            getApplicationContext().getTheme());
        } else {
            characterDrawable =
                    getResources().getDrawable(R.drawable.wizzard_f_hit_anim_f0,
                            getApplicationContext().getTheme());
        }
        int imageWidth = ((BitmapDrawable) characterDrawable).getBitmap().getWidth();
        int imageHeight = ((BitmapDrawable) characterDrawable).getBitmap().getHeight();
        player.setWidth(imageWidth);
        player.setHeight(imageHeight);
        playerImageView.setImageDrawable(characterDrawable);
    }

    /**
     * Display enemies based on map layout.
     */
    private void displayEnemies() {
        Drawable enemyDrawable1 = null;
        Drawable enemyDrawable2 = null;
        ImageView enemyView1 = new ImageView(MainActivity.this);
        ImageView enemyView2 = new ImageView(MainActivity.this);
        if (mapIndex == 0) {
            enemyDrawable1 =
                    getResources().getDrawable(R.drawable.masked_orc_idle_anim_f0,
                            getApplicationContext().getTheme());
            enemyDrawable2 =
                    getResources().getDrawable(R.drawable.ogre_idle_anim_f0,
                            getApplicationContext().getTheme());
            enemyView1.setImageDrawable(enemyDrawable1);
            enemyView2.setImageDrawable(enemyDrawable2);
            enemyFactory1 = new OrcFactory(enemyView1);
            enemyFactory2 = new OgreFactory(enemyView2);
            enemy1 = enemyFactory1.createEnemy();
            enemy2 = enemyFactory2.createEnemy();
        } else if (mapIndex == 1) {
            enemyDrawable1 =
                    getResources().getDrawable(R.drawable.ogre_idle_anim_f0,
                            getApplicationContext().getTheme());
            enemyDrawable2 =
                    getResources().getDrawable(R.drawable.big_zombie_idle_anim_f0,
                            getApplicationContext().getTheme());
            enemyView1.setImageDrawable(enemyDrawable1);
            enemyView2.setImageDrawable(enemyDrawable2);
            enemyFactory1 = new OgreFactory(enemyView1);
            enemyFactory2 = new ZombieFactory(enemyView2);
            enemy1 = new OgreEnemy(enemyView1, 2, 4);
            enemy2 = new ZombieEnemy(enemyView2, 6, 1);
        } else if (mapIndex == 2) {
            enemyDrawable1 =
                    getResources().getDrawable(R.drawable.big_zombie_idle_anim_f0,
                            getApplicationContext().getTheme());
            enemyDrawable2 =
                    getResources().getDrawable(R.drawable.big_demon_idle_anim_f0,
                            getApplicationContext().getTheme());
            enemyView1.setImageDrawable(enemyDrawable1);
            enemyView2.setImageDrawable(enemyDrawable2);
            enemyFactory1 = new ZombieFactory(enemyView1);
            enemyFactory2 = new DemonFactory(enemyView2);
            enemy1 = new ZombieEnemy(enemyView1, 2, 4);
            enemy2 = new DemonEnemy(enemyView2, 6, 1);
        } else {
            return;
        }
        enemyImageView1.setImageDrawable(enemyDrawable1);
        enemyImageView2.setImageDrawable(enemyDrawable2);
        enemyImageView1.setVisibility(View.VISIBLE);
        enemyImageView2.setVisibility(View.VISIBLE);
        initializeEnemyPos();
    }

    /**
     * Display powerups.
     */
    private void displayPowerups() {
        player.resetPowerups();
        weaponDisplay.setImageResource(R.drawable.weapon_regular_sword);
        weaponDisplay2.setImageResource(R.drawable.weapon_regular_sword);
        Drawable powerupDrawable1 = null;
        Drawable powerupDrawable2 = null;
        ImageView powerupView1 = new ImageView(MainActivity.this);
        ImageView powerupView2 = new ImageView(MainActivity.this);
        if (mapIndex == 0) {
            CollectablePowerup heart = new CollectablePowerup();
            powerupDrawable1 =
                    getResources().getDrawable(R.drawable.ui_heart_full,
                            getApplicationContext().getTheme());
            powerupDrawable2 =
                    getResources().getDrawable(R.drawable.coin_anim_f0,
                            getApplicationContext().getTheme());
            powerupView1.setImageDrawable(powerupDrawable1);
            powerupView2.setImageDrawable(powerupDrawable2);
        } else if (mapIndex == 1) {
            powerupDrawable1 =
                    getResources().getDrawable(R.drawable.chest_full_open_anim_f2,
                            getApplicationContext().getTheme());
            powerupDrawable2 =
                    getResources().getDrawable(R.drawable.ui_heart_full,
                            getApplicationContext().getTheme());
            powerupView1.setImageDrawable(powerupDrawable1);
            powerupView2.setImageDrawable(powerupDrawable2);

        } else if (mapIndex == 2) {
            powerupDrawable1 =
                    getResources().getDrawable(R.drawable.chest_full_open_anim_f2,
                            getApplicationContext().getTheme());
            powerupDrawable2 =
                    getResources().getDrawable(R.drawable.coin_anim_f0,
                            getApplicationContext().getTheme());
            powerupView1.setImageDrawable(powerupDrawable1);
            powerupView2.setImageDrawable(powerupDrawable2);
        } else {
            return;
        }
        powerupImageView1.setImageDrawable(powerupDrawable1);
        powerupImageView2.setImageDrawable(powerupDrawable2);
        powerupImageView1.setVisibility(View.VISIBLE);
        powerupImageView2.setVisibility(View.VISIBLE);
        initializePowerupPos();
    }

    /**
     * Initialize enemy position.
     */
    private void initializeEnemyPos() {
        int enemy1x;
        int enemy1y;
        int enemy2x;
        int enemy2y;
        if (mapIndex == 0) {
            enemy1x = 150;
            enemy1y = 1500;
            enemy2x = 800;
            enemy2y = 1000;
        } else if (mapIndex == 1) {
            enemy1x = 300;
            enemy1y = 500;
            enemy2x = 600;
            enemy2y = 800;
        } else {
            enemy1x = 300;
            enemy1y = 900;
            enemy2x = 850;
            enemy2y = 600;
        }
        enemyImageView1.setX(enemy1x);
        enemyImageView1.setY(enemy1y);
        enemyImageView2.setX(enemy2x);
        enemyImageView2.setY(enemy2y);
        enemy1.setX(enemy1x);
        enemy1.setY(enemy1y);
        enemy2.setX(enemy2x);
        enemy2.setY(enemy2y);
    }

    /**
     * Initialize powerups pos.
     */
    public void initializePowerupPos() {
        int powerup1x;
        int powerup1y;
        int powerup2x;
        int powerup2y;
        if (mapIndex == 0) {
            powerup1x = 900;
            powerup1y = 1500;
            powerup2x = 400;
            powerup2y = 1000;
        } else if (mapIndex == 1) {
            powerup1x = 300;
            powerup1y = 700;
            powerup2x = 800;
            powerup2y = 1500;
        } else {
            powerup1x = 300;
            powerup1y = 400;
            powerup2x = 750;
            powerup2y = 1000;
        }
        powerupImageView1.setX(powerup1x);
        powerupImageView1.setY(powerup1y);
        powerupImageView2.setX(powerup2x);
        powerupImageView2.setY(powerup2y);
    }

    /**
     * Get username.
     * @return username
     */
    public TextView getUserName() {
        return userName;
    }

    /**
     * Get starting health points.
     * @param difficultyText string
     * @return int
     */
    public static int getStartingHP(String difficultyText) {
        switch (difficultyText.toLowerCase()) {
        case "Easy":
            return 150;
        case "Normal":
            return 100;
        case "Hard":
            return 50;
        default:
            throw new IllegalArgumentException("Invalid difficulty: " + difficultyText);
        }
    }

    /**
     * Set Screen Dims.
     * @param gameSessionViewModel game session view model
     */
    public void setScreenDims(GameSessionViewModel gameSessionViewModel) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        gameSessionViewModel.sendScreenDims(width, height);
    }

    /**
     * Get constraint layout.
     * @return constraint layout
     */
    public ConstraintLayout getConstraintLayout() {
        return constraintLayout;
    }

    /**
     * Get Player imageview.
     * @return player image view
     */
    public ImageView getPlayerImageView() {
        return playerImageView;
    }

    /**
     * On Destroy.
     */
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        footstepPlayerLeftRight.release();
        footstepPlayerUp.release();
        footstepPlayerDown.release();
        damageDone.release();
        scoreHandler.removeCallbacksAndMessages(scoreRunnable);
    }

    public boolean heartPos() {
        return true;
    }

    public boolean coinPos() {
        return true;
    }

    public boolean chestPos() {
        return true;
    }

}
