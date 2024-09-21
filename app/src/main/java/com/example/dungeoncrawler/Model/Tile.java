package com.example.dungeoncrawler.Model;
import com.example.dungeoncrawler.View.FloorTile;
import com.example.dungeoncrawler.View.LadderTile;
import com.example.dungeoncrawler.View.WallTile;

import android.graphics.Rect;
import android.graphics.Canvas;

public abstract class Tile {

    protected final Rect mapLocationRect;

    /**
     * Get map location rect.
     * @param mapLocationRect tile
     */
    public Tile(Rect mapLocationRect) {
        this.mapLocationRect = mapLocationRect;
    }

    /**
     * Tile Type enum.
     */
    public enum TileType {
        WALL_TILE,
        PLACEHOLDER1,
        PLACEHOLDER2,
        FLOOR_TILE,
        LADDER_TILE,
    }

    /**
     * Tile get tile.
     * @param idxTileType int
     * @param spriteSheet sprite sheet
     * @param mapLocationRect rec
     * @return tile
     */
    public static Tile getTile(int idxTileType, SpriteSheet spriteSheet, Rect mapLocationRect) {
        switch (TileType.values()[idxTileType]) {
        case FLOOR_TILE:
            return new FloorTile(spriteSheet, mapLocationRect);
        case WALL_TILE:
            return new WallTile(spriteSheet, mapLocationRect);
        case LADDER_TILE:
            return new LadderTile(spriteSheet, mapLocationRect);
        default:
            return null;
        }
    }

    /**
     * Draw.
     * @param canvas canvas
     */
    public abstract void draw(Canvas canvas);
}
