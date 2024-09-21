package com.example.dungeoncrawler.Model;

import android.util.Log;
import android.widget.ImageView;

public class OrcEnemy extends Enemy {
    private ImageView imageView;
    private int speed = 10;
    private int size;

    /**
     * Orc Enemy.
     * @param imageView image view
     * @param speed int
     * @param size int
     */
    public OrcEnemy(ImageView imageView, int speed, int size) {
        //change speed and size
        super(speed, 60, "masked_orc_idle_anim_f0", imageView);
        this.imageView = imageView;
        this.speed = speed;
        this.size = size;
        Log.d("lemon", "is imagviewNull" + (this.imageView == null));
    }

    @Override
    protected void destroy() {
        //implement
    }

    @Override
    public void move() {
        this.setX(this.getX() + speed * this.getDirection());
    }


    public boolean moveLeft() {
        return false;
    }

    public boolean moveRight() {
        return true;
    }

    public boolean collided() {
        return true;
    }

    @Override
    public int getYDirection() {
        return 0;
    }

    @Override
    public void setYDirection(int dir) {
        dir = dir;
    }
}