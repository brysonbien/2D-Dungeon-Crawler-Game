package com.example.dungeoncrawler.Model;
import com.example.dungeoncrawler.View.Sprite;
import com.example.dungeoncrawler.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class SpriteSheet {

    private static final int SPRITE_WIDTH_PIXELS = 16;
    private static final int SPRITE_HEIGHT_PIXELS = 16;
    private Bitmap bitmap;

    /**
     * Sprite sheet.
     * @param context context
     */
    public SpriteSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.sprite_sheet, bitmapOptions);
    }

    /**
     * Get bitmap.
     * @return bitmap
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * Get floor sprite.
     * @return sprite
     */
    public Sprite getFloorSprite() {
        return getSpriteByIndex(3, 0);
    }

    /**
     * Get wall sprite.
     * @return sprite
     */
    public Sprite getWallSprite() {
        return getSpriteByIndex(0, 0); }

    /**
     * Get ladder sprite.
     * @return sprite
     */
    public Sprite getLadderSprite() {
        return getSpriteByIndex(5, 2); }

    /**
     * Get sprite by index.
     * @param idxRow sprite
     * @param idxCol sprite
     * @return sprite
     */
    private Sprite getSpriteByIndex(int idxRow, int idxCol) {
        return new Sprite(this, new Rect(
                16 + idxCol * SPRITE_WIDTH_PIXELS,
                16 + idxRow * SPRITE_HEIGHT_PIXELS,
                16 + (idxCol + 1) * SPRITE_WIDTH_PIXELS,
                16 + (idxRow + 1) * SPRITE_HEIGHT_PIXELS
        ));
    }
}
