package com.example.dungeoncrawler.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.example.dungeoncrawler.Model.TileMap;

/*
    responsible for rendering a tile map on the screen
 */
public class TileMapView extends View {
    private TileMap tileMap;

    /**
     * Tile Map View.
     * @param context context
     * @param tileMap tilemap
     */
    public TileMapView(Context context, TileMap tileMap) {
        super(context);
        this.tileMap = tileMap;
    }

    @Override
    /**
     * On Draw (Canvas).
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("helpme", "here");

        // Draw the tiles from the TileMap
        Rect visibleRect = new Rect(0, 0, getWidth(), getHeight());
        //tileMap.draw(canvas, visibleRect);
        tileMap.draw(canvas);
    }
}
