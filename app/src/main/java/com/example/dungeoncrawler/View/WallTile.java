package com.example.dungeoncrawler.View;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.dungeoncrawler.Model.Tile;
import com.example.dungeoncrawler.Model.SpriteSheet;

public class WallTile extends Tile {

    private final Sprite sprite;

    /**
     * Wall Tile.
     * @param spriteSheet sprite sheet
     * @param mapLocationRect rect
     */
    public WallTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getWallSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
