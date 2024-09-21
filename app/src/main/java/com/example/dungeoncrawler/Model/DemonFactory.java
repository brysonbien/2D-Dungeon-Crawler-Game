package com.example.dungeoncrawler.Model;

import android.widget.ImageView;

public class DemonFactory extends EnemyFactory {
    private ImageView imageView;

    /**
     * Demon Factory.
     * @param imageView image view
     */
    public DemonFactory(ImageView imageView) {
        this.imageView = imageView;
    }
    @Override
    public Enemy createEnemy() {
        //update this
        int speed = 10;
        int size = 0;

        DemonEnemy demonEnemy = new DemonEnemy(imageView, speed, size);
        return demonEnemy;
    }
}
