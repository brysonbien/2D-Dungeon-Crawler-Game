package com.example.dungeoncrawler.Model;

import android.util.Log;
import android.widget.ImageView;

public class OgreEnemy extends Enemy {
    private ImageView imageView;
    private int speed = 5;
    private int size;
    private int yDir = 0;

    /**
     * Ogre Enemy.
     * @param imageView imageView
     * @param speed int
     * @param size int
     */
    public OgreEnemy(ImageView imageView, int speed, int size) {
        //change speed and size
        super (speed, 70, "ogre_idle_anim_f0", imageView);
        this.imageView = imageView;
        this.speed = speed;
        this.size = size;
        this.yDir = yDir;
    }

    @Override
    protected void destroy() {
        //implement
    }

    @Override
    public void move() {
        this.setX(this.getX() + speed * this.getDirection());
        this.setY(this.getY() + speed * yDir);
        Log.d("ogre", "settingy " + getY());
    }

    @Override
    public int getYDirection() {
        return this.yDir;
    }
    @Override
    public void setYDirection(int dir) {
        this.yDir = dir;
        Log.d("ogre", "neg" + yDir);
    }

    public boolean OgreMoveSquareRightUp() {
        return true;
    }

    public boolean OgreMoveSquareLeftBot() {
        return true;
    }

    public boolean Collided() {
        return true;
    }

}



