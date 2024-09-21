package com.example.dungeoncrawler.Model;
import static com.example.dungeoncrawler.Model.MapLayout.NUMBER_OF_COL_TILES;
import static com.example.dungeoncrawler.Model.MapLayout.NUMBER_OF_ROW_TILES;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.dungeoncrawler.View.MainActivity;

public abstract class Enemy {
    //attributes
    private int speed;
    private int direction;
    private int size;
    private boolean isDead;
    private int y;
    private int x;
    private String spriteFile;
    private ImageView imageView;
    private GameSession gameSession = GameSession.getInstance();

    /**
     * Enemy class.
     * @param speed int
     * @param size int
     * @param spriteFile String
     * @param imageView ImageView
     */
    public Enemy (int speed, int size, String spriteFile, ImageView imageView) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        this.spriteFile = spriteFile;
        this.imageView = imageView;
        this.direction = 1;
        this.isDead = false;
    }

    /**
     * Destroy.
     */
    protected abstract void destroy();

    /**
     * Move.
     */
    public abstract void move();

    /**
     * Get X.
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * Get Y.
     * @return int
     */
    public int getY() {
        return y;
    }

    /**
     * Set X.
     * @param x int
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set Y.
     * @param y int
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Display.
     */
    public void display() {
        imageView.setImageResource(getResourceIdByFileName(spriteFile));
    }

    /**
     * Get Resource Id By Filename
     * @param spriteFile String
     * @return int
     */
    private int getResourceIdByFileName(String spriteFile) {
        return imageView.getResources().getIdentifier(spriteFile, "drawable", imageView.getContext().getPackageName());
    }

    /**
     * Get Width.
     * @return int
     */
    public int getWidth() {
        return getBitmapFromResource().getWidth();
    }

    /**
     * Get Height.
     * @return int
     */
    public int getHeight() {
        return getBitmapFromResource().getHeight();
    }

    /**
     * Get Bitmap From Resource.
     * @return bitmap
     */
    private Bitmap getBitmapFromResource() {
        int resourceId = getResourceIdByFileName(spriteFile);
        return BitmapFactory.decodeResource(imageView.getResources(), resourceId);
    }

    /**
     * Get Image View.
     * @return Image View
     */
    public ImageView getImageView() {
        Log.d("lemon", "enemyclass" + (this.imageView == null));
        return imageView;
    }

    /**
     * Get Direction.
     * @return int
     */
    public int getDirection() {
        return this.direction;
    }

    /**
     * Set Direction.
     * @param direction int
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Get Y Direction.
     * @return int
     */
    public abstract int getYDirection();

    /**\
     * Set Y Direction.
     * @param dir int
     */
    public abstract void setYDirection(int dir);
    public boolean getIsDead() {
        return isDead;
    }
    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

//    public void checkCollisions(TileMap tileMap) {
//        String collisionSide = "NONE";
//        int wall = 0;
//        int tileWidth = (int) (gameSession.getTileImageDimInfo() * gameSession.getTileMapScale());
//        int tileHeight = (int) (gameSession.getTileImageDimInfo() * gameSession.getTileMapScale());
//        int tileMapStartingX = gameSession.getTileMapX();
//        int tileMapStartingY = gameSession.getTileMapY();
//        for (int i = 0; i < NUMBER_OF_ROW_TILES; i++) {
//            for (int j = 0; j < NUMBER_OF_COL_TILES; j++) {
//                if (tileMap.getMapLayout().getLayout()[i][j] == wall) {
//                    int leftOfTile = tileMapStartingX + tileWidth * (j);
//                    int rightOfTile = tileMapStartingX + tileWidth * (j + 1);
//                    int topOfTile = tileMapStartingY + tileHeight * (i);
//                    int bottomOfTile = tileMapStartingY + tileHeight * (i + 1);
//                    int topOfCharacter = y + tileMapStartingY;
//                    int bottomOfCharacter = y + getHeight() + tileMapStartingY;
//                    int leftOfCharacter = x + tileMapStartingX;
//                    int rightOfCharacter = x + getWidth() + tileMapStartingX;
//                    if (i == 16 && j == NUMBER_OF_COL_TILES - 1) {
//                        Log.d("grapefruit", "leftoftile" + leftOfTile + " leftofenemy" + leftOfCharacter);
//                    }
//                    boolean xOverlaps = (leftOfCharacter < rightOfTile)
//                            && (rightOfCharacter > leftOfTile);
//                    boolean yOverlaps = (bottomOfCharacter > topOfTile)
//                            && (topOfCharacter < bottomOfTile);
//                    if (xOverlaps && yOverlaps) {
//                        Log.d("grapefruit", "here");
//                        if (tileMap.getMapLayout().getLayout()[i][j] == wall) {
//                            if (direction == 1) {
//                                direction = -1;
//                            } else {
//                                direction = 1;
//                            }
//                        }
//                    }
//                    Log.d("blueberry", "" + x);
//                }
//            }
//        }
//        move();
//    }
}
