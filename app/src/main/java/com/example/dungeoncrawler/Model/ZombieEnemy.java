package com.example.dungeoncrawler.Model;

import android.widget.ImageView;

public class ZombieEnemy extends Enemy {
    private ImageView imageView;
    private int speed = 5;
    private int size;

    /**
     * Zombie Enemy
     * @param imageView imageview
     * @param speed int
     * @param size int
     */
    public ZombieEnemy(ImageView imageView, int speed, int size) {
        //change speed and size
        super(speed, 5, "big_zombie_idle_anim_f0", imageView);
        this.imageView = imageView;
        this.speed = speed;
        this.size = size;
    }

    @Override
    protected void destroy() {
        //implement
    }
    @Override
    public void move() {
        this.setY(this.getY() + speed * this.getDirection());
    }

    @Override
    public int getYDirection() {
        return 0;
    }

    @Override
    public void setYDirection(int dir) {
        dir = dir;
    }

    public boolean moveTop() {
        return true;
    }

    public boolean moveBottom() {
        return true;
    }

    public boolean Collided() {
        return true;
    }
}



