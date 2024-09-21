package com.example.dungeoncrawler.Model;

import android.util.Log;
import android.widget.ImageView;

public class OrcFactory extends EnemyFactory {
    private ImageView imageView;

    /**
     * Orc Factory.
     * @param imageView image view
     */
    public OrcFactory(ImageView imageView) {
        this.imageView = imageView;
    }
    @Override
    public Enemy createEnemy() {
        //update this
        int speed = 10;
        int size = 0;

        OrcEnemy orcEnemy = new OrcEnemy(imageView, speed, size);
        Log.d("lemon", "createEnemy" + (this.imageView == null));
        return orcEnemy;
    }
}
