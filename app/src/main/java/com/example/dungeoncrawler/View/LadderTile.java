package com.example.dungeoncrawler.View;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.dungeoncrawler.Model.Tile;
import com.example.dungeoncrawler.Model.SpriteSheet;

public class LadderTile extends Tile {

    private final Sprite sprite;

    /**
     * Ladder Tile.
     * @param spriteSheet sprite sheet
     * @param mapLocationRect map location rect
     */
    public LadderTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getLadderSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
