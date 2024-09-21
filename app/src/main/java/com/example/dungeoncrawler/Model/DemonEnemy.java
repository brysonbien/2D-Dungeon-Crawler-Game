package com.example.dungeoncrawler.Model;
import android.widget.ImageView;

public class DemonEnemy extends Enemy {
    private ImageView imageView;
    private int speed = 10;
    private int size;
    private int yDir = 1;

    /**
     * Demon Enemy.
     * @param imageView image view
     * @param speed int
     * @param size int
     */
    public DemonEnemy(ImageView imageView, int speed, int size) {
        //change speed and size
        super(speed, 30, "big_demon_idle_anim_f0", imageView);
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
        this.setX(this.getX() + speed * this.getDirection());
        this.setY(this.getY() + speed * this.getYDirection());
    }

    @Override
    public int getYDirection() {
        return yDir;
    }

    @Override
    public void setYDirection(int dir) {
        this.yDir = dir;
    }

    public boolean demonMovetopDiagonal() {
        return true;
    }

    public boolean demonMoveBotDiagonal() {
        return false;
    }

    public boolean Collided() {
        return false;
    }

}
