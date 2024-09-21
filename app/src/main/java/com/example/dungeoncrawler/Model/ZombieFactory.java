package com.example.dungeoncrawler.Model;

import android.widget.ImageView;

public class ZombieFactory extends EnemyFactory {
    private ImageView imageView;

    /**
     * Zombie Factory.
     * @param imageView image view
     */
    public ZombieFactory(ImageView imageView) {
        this.imageView = imageView;
    }
    @Override
    public Enemy createEnemy() {
        //update this
        int speed = 5;
        int size = 0;
        ZombieEnemy zombieEnemy = new ZombieEnemy(imageView, speed, size);
        return zombieEnemy;
    }
}
