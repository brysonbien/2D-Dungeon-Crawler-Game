package com.example.dungeoncrawler.Model;

import static com.example.dungeoncrawler.Model.MapLayout.NUMBER_OF_COL_TILES;
import static com.example.dungeoncrawler.Model.MapLayout.NUMBER_OF_ROW_TILES;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;

import com.example.dungeoncrawler.ViewModel.ConfigViewModel;

import java.util.Observer;
import java.util.Observable;

public class Player implements Observer {
    private static Player player;
    private static int speed = 8;
    private static int x;
    private int initX;
    private int initY;
    private static int y;
    private int width;
    private int height;
    private int hp;
    private int difficulty;
    private GameSession gameSession = GameSession.getInstance();
    private MovementStrategy movementStrategy;
    private int damage;
    private boolean hasGoldenSword;
    private boolean isArmed;
    private String powerupType = null;


    @Override
    public void update(Observable observable, Object o) {
        move();
    }

    private Player() { }

    /**
     * Player instance.
     * @return player
     */
    public static Player getInstance() {
        if (player == null) {
            player = new Player();
            player.x = 0;
            player.y = 0;
            player.difficulty = ConfigViewModel.getDifficulty();
            player.initializeHP(player.difficulty);
            player.initializeDamage();
            player.hasGoldenSword = false;
            player.isArmed = false;
        }
        return player;
    }

    private void initializeDamage() {
        damage = 50;
    }

    /**
     * Set movement strategy.
     * @param strategy movement strategy
     */
    public void setMovementStrategy(MovementStrategy strategy) {
        movementStrategy = strategy;
    }

    /**
     * Movement strategy.
     */
    public void move() {
        movementStrategy.move(this);
    }

    /**
     * Check collisions.
     * @param tileMap tile map
     * @param keyCode int
     * @return boolean
     */
    public boolean checkCollisions(TileMap tileMap, int keyCode) {
        String collisionSide = "NONE";
        int wall = 0;
        int exit = 4;
        int tileWidth = (int) (gameSession.getTileImageDimInfo() * gameSession.getTileMapScale());
        int tileHeight = (int) (gameSession.getTileImageDimInfo() * gameSession.getTileMapScale());
        // NOTE!! this is hard coded, fix later
        //int tileMapStartingX = 54;
        //int tileMapStartingY = 322;
        int tileMapStartingX = gameSession.getTileMapX();
        int tileMapStartingY = gameSession.getTileMapY();
        Log.d("help2", "x: " + tileMapStartingX + " y: " + tileMapStartingY);
        for (int i = 0; i < NUMBER_OF_ROW_TILES; i++) {
            for (int j = 0; j < NUMBER_OF_COL_TILES; j++) {
                if (tileMap.getMapLayout().getLayout()[i][j] == wall
                        || tileMap.getMapLayout().getLayout()[i][j] == exit) {
                    int leftOfTile = tileMapStartingX + tileWidth * (j);
                    int rightOfTile = tileMapStartingX + tileWidth * (j + 1);
                    int topOfTile = tileMapStartingY + tileHeight * (i);
                    int bottomOfTile = tileMapStartingY + tileHeight * (i + 1);
                    int topOfCharacter = y;
                    int bottomOfCharacter = y + height;
                    int leftOfCharacter = x;
                    int rightOfCharacter = x + width;
                    boolean xOverlaps = (leftOfCharacter < rightOfTile)
                            && (rightOfCharacter > leftOfTile);
                    boolean yOverlaps = (bottomOfCharacter > topOfTile)
                            && (topOfCharacter < bottomOfTile);
                    if (xOverlaps && yOverlaps) {
                        if (tileMap.getMapLayout().getLayout()[i][j] == wall) {
                            if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                                player.setMovementStrategy(new MoveDown());
                            } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                                player.setMovementStrategy(new MoveUp());
                            } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                                player.setMovementStrategy(new MoveRight());
                            } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                                player.setMovementStrategy(new MoveLeft());
                            }
                            player.move();
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Initialize Hp.
     * @param difficulty double
     */
    public void initializeHP(int difficulty) {
        this.difficulty = difficulty;
        switch (difficulty) {
            case 1:
                hp = 150;
                break;
            case 2:
                hp = 100;
                break;
            case 3:
                hp = 50;
                break;
        }
    }

    /**
     * Check Enemy Collisions
     * @param enemy Enemy
     * @return boolean
     */
    public boolean checkEnemyCollisions(Enemy enemy) {
        if (!enemy.getIsDead()) {
            int playerX = player.getX();
            int playerY = player.getY();

            int enemyX = enemy.getX();
            int enemyY = enemy.getY();
            int enemyWidth = enemy.getWidth();
            int enemyHeight = enemy.getHeight();

            boolean xOverlaps = (playerX < enemyX + enemyWidth * 1.8) && (playerX + width > enemyX);
            boolean yOverlaps = (playerY < enemyY + enemyHeight * 1.8) && (playerY + height > enemyY);
            //Log.d(enemy.getX())
            if (enemyX == 800) {
                Log.d("raspberry", "pL " + playerX + ", eR " + (enemyX + enemyWidth * 2) + ", pR " + (playerX + width) + ", eL " + enemyX);
            }
            //Log.d("raspberry", "enemyY " + enemyY + ", playerY, " + playerY + "," + enemyHeight);

            if (xOverlaps && yOverlaps) {
                Log.d("raspberry", "CRASH!");
                if (isArmed) {
                    enemy.setIsDead(true);
                } else {
                    player.setX(initX);
                    player.setY(initY);
                    reduceHPOnCollision();
                    if (enemy instanceof DemonEnemy) {
                        hp -= 10;
                    } else if (enemy instanceof ZombieEnemy) {
                        hp -= 5;
                    } else if (enemy instanceof OgreEnemy) {
                        hp -= 3;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public int checkPowerupCollisions(int mapIndex, Drawable powerup1, Drawable powerup2) {
        int num = 0;
        int w1 = powerup1.getIntrinsicWidth() * 2;
        int h1 = powerup1.getIntrinsicHeight() * 2;
        int w2 = powerup2.getIntrinsicWidth() * 2;
        int h2 = powerup2.getIntrinsicHeight() * 2;
        Log.d("dims", "w1: " + w1 + "h1: " + h1 + "w2: " + w2 + "h2: " + h2);
        Log.d("dims", "x: " + x + "y: " + y);
        if (mapIndex == 0) {
            //heart
            if (x + width > 900 && x < 900 + w1 && y + height > 1500 && y < 1500 + h1) {
                num = 1;
                hp += 5;
                powerupType = "heart";
            }
            //coin
            if (x + width > 400 && x < 400 + w2 && y + height > 1000 && y < 1000 + h2) {
                num = 2;
                speed *= 1.25;
                powerupType = "coin";
            }
        } else if (mapIndex == 1) {
            //chest
            if (x + width > 300 && x < 300 + w1 && y + height > 700 && y < 700 + h1) {
                player.hasGoldenSword = true;
                num = 1;
                powerupType = "chest";
            }
            //heart
            if (x + width > 800 && x < 800 + w2 && y + height > 1500 && y < 1500 + h2) {
                num = 2;
                hp += 5;
                powerupType = "heart";
            }
        } else {
            //chest
            if (x > 300 && x < 300 + w1 && y + height > 400 && y < 400 + h1) {
                player.hasGoldenSword = true;
                num = 1;
                powerupType = "chest";
            }
            //coin
            if (x > 750 && x < 750 + w2 && y + height > 1000 && y < 1000 + h2) {
                num = 2;
                speed *= 1.25;
                powerupType = "coin";
            }
        }
        return num;
    }

    /**
     * get powerupType
     * @return string
     */
    public String getPowerupType() {
        return powerupType;
    }

    /**
     * Reduce HP on Collision.
     */
    public void reduceHPOnCollision() {
        switch (difficulty) {
            case 1:
                hp -= 5;
                break;
            case 2:
                hp -= 10;
                break;
            case 3:
                hp -= 15;
                break;
        }
        if (hp <= 0) {
            hp = 0;
        }
    }

    /**
     * Get Hp.
     * @return int
     */
    public int getHp() {return hp;}

    /**
     * Set Hp.
     */
    public void setHp(int i) {hp = i;}

    /**
     * Get damage.
     * @return int
     */
    public int getDamage() {return damage;}

    /**
     * Get damage.
     */
    public void setDamage(int d) {damage = d;}

    /**
     * Get x.
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * Get y.
     * @return int
     */
    public int getY() {
        return y;
    }

    public void setInitX(int x) {
        this.initX = x;
    }

    public void setInitY(int y) {
        this.initY = y;
    }

    /**
     * Set x.
     * @param x int
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set y.
     * @param y int
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Set coordinates.
     * @param x int
     * @param y int
     */
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set width
     * @param imageWidth int
     */
    // NOTE!! the next 3 are hard coded
    public void setWidth(int imageWidth) {
        //this.width = imageWidth * 2;
        this.width = 80;
    }

    /**
     * Set height.
     * @param imageHeight int
     */
    public void setHeight(int imageHeight) {
        //this.height = imageHeight * 2;
        this.height = 112;
    }

    /**
     * Get width.
     * @return int
     */
    public int getWidth() {
        return 2;
    }

    /**
     * Get speed.
     * @return int
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Set speed.
     * @param i int
     */
    public void setSpeed(int i) {
        speed = i;
    }

    /**
     * Get height.
     * @return int
     */
    public int getHeight() {
        return height;
    }
    public boolean getIsArmed() {
        return this.isArmed;
    }

    public void setIsArmed(boolean isArmed) {
        this.isArmed = isArmed;
    }
    public boolean getHasGoldenSword() {
        return this.hasGoldenSword;
    }

    public void setHasGoldenSword(boolean hasGoldenSword) {
        this.hasGoldenSword = hasGoldenSword;
    }

    public void resetPowerups() {
        hasGoldenSword = false;
        speed = 8;
    }

    public void applyPowerup(CollectablePowerup collectablePowerup) {
        while (1==1) {
            break;
        }
    }

    public boolean getGoldenSword() {
        return true;
    }

    public boolean resetPower() {
        return true;
    }

    public boolean Armed() {
        return true;
    }
}
