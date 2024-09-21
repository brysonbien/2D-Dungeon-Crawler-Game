package com.example.dungeoncrawler.Model;

import android.widget.ImageView;

public class OgreFactory extends EnemyFactory {
    private ImageView imageView;

    /**
     * Ogre Factory.
     * @param imageView image view
     */
    public OgreFactory(ImageView imageView) {
        this.imageView = imageView;
    }
    @Override
    public Enemy createEnemy() {
        //update this
        int speed = 5;
        int size = 0;

        OgreEnemy ogreEnemy = new OgreEnemy(imageView, speed, size);
        return ogreEnemy;
    }
}
