package com.example.dungeoncrawler.View;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.dungeoncrawler.Model.Tile;
import com.example.dungeoncrawler.Model.SpriteSheet;

public class FloorTile extends Tile {

    private final Sprite sprite;

    /**
     * Floor Tile.
     * @param spriteSheet sprite sheet
     * @param mapLocationRect map location Rect
     */
    public FloorTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getFloorSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}

