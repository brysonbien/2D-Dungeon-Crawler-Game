package com.example.dungeoncrawler.Model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import static com.example.dungeoncrawler.Model.MapLayout.NUMBER_OF_COL_TILES;
import static com.example.dungeoncrawler.Model.MapLayout.NUMBER_OF_ROW_TILES;
import static com.example.dungeoncrawler.Model.MapLayout.TILE_WIDTH_PIXELS;
import static com.example.dungeoncrawler.Model.MapLayout.TILE_HEIGHT_PIXELS;

import java.util.ArrayList;
import java.util.List;


/*
    manages and draws a map composed of the tiles
 */
public class TileMap {

    private final MapLayout mapLayout;
    private Tile[][] tilemap;
    private SpriteSheet spriteSheet;
    private Bitmap mapBitmap;

    private List<Powerup> powerups = new ArrayList<>();

    /**
     * Tile map.
     * @param spriteSheet sprite sheet
     * @param index int
     */
    public TileMap(SpriteSheet spriteSheet, int index) {
        mapLayout = new MapLayout(index);
        tilemap = new Tile[NUMBER_OF_ROW_TILES][NUMBER_OF_COL_TILES];
        this.spriteSheet = spriteSheet;
        initializeTileMap();
    }

    /**
     * Initialize tile map.
     */
    private void initializeTileMap() {
        int[][] layout = mapLayout.getLayout();

        tilemap = new Tile[NUMBER_OF_ROW_TILES][NUMBER_OF_COL_TILES];

        for (int iRow = 0; iRow < NUMBER_OF_ROW_TILES; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_COL_TILES; iCol++) {
                tilemap[iRow][iCol] = Tile.getTile(
                        layout[iRow][iCol],
                        spriteSheet,
                        getRectByIndex(iRow, iCol)
                );
            }
        }

        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        mapBitmap = Bitmap.createBitmap(
                NUMBER_OF_COL_TILES * TILE_WIDTH_PIXELS,
                NUMBER_OF_ROW_TILES * TILE_HEIGHT_PIXELS,
                config
        );

        Canvas mapCanvas = new Canvas(mapBitmap);

        for (int iRow = 0; iRow < NUMBER_OF_ROW_TILES; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_COL_TILES; iCol++) {
                tilemap[iRow][iCol].draw(mapCanvas);
            }
        }
    }

    /**
     * Get react by index.
     * @param idxRow int
     * @param idxCol int
     * @return rect
     */
    private Rect getRectByIndex(int idxRow, int idxCol) {
        return new Rect(
                idxCol * TILE_WIDTH_PIXELS,
                idxRow * TILE_HEIGHT_PIXELS,
                (idxCol + 1) * TILE_WIDTH_PIXELS,
                (idxRow + 1) * TILE_HEIGHT_PIXELS
        );
    }

    /**
     * Draw.
     * @param canvas canvas
     */
    public void draw(Canvas canvas) {
        // Loop through the tiles and draw them
        for (int row = 0; row < NUMBER_OF_ROW_TILES; row++) {
            for (int col = 0; col < NUMBER_OF_COL_TILES; col++) {
                tilemap[row][col].draw(canvas);
            }
        }
    }

    /**
     * Get map bitmap.
     * @return bitmap
     */
    public Bitmap getMapBitmap() {
        return mapBitmap;
    }

    /**
     * Get map layout.
     * @return map layout.
     */
    public MapLayout getMapLayout() {
        return mapLayout;
    }
}

