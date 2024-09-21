package com.example.dungeoncrawler.View;
import com.example.dungeoncrawler.Model.SpriteSheet;

import android.graphics.Rect;
import android.graphics.Canvas;

public class Sprite {

    private final SpriteSheet spriteSheet;
    private final Rect rect;

    /**
     * Sprite sheet and rect.
     * @param spriteSheet spritesheet
     * @param rect rect
     */
    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    /**
     * Draw.
     * @param canvas canvas
     * @param x int
     * @param y int
     */
    public void draw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(
                spriteSheet.getBitmap(),
                rect,
                new Rect(x, y, x + getWidth(), y + getHeight()),
                null
        );
    }

    /**
     * Get Width.
     * @return rect
     */
    public int getWidth() {
        return rect.width();
    }

    /**
     * Get height.
     * @return rect
     */
    public int getHeight() {
        return rect.height();
    }
}
