package com.example.dungeoncrawler.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import java.util.ArrayList;
import java.util.List;

public class SpriteAnimationView extends AppCompatImageView {
    private final List<Bitmap> frames = new ArrayList<>();
    private int currentFrameIndex = 0;
    private final long frameDuration = 100; // Adjust the frame duration as needed
    private long lastFrameChangeTime = 0;

    /**
     * Sprite animation view.
     * @param context context
     * @param attrs attribute set
     */
    public SpriteAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Set sprite sheet.
     * @param resourceId int
     * @param frameCount int
     */
    public void setSpriteSheet(int resourceId, int frameCount) {
        Bitmap spriteSheet = BitmapFactory.decodeResource(getResources(), resourceId);
        int frameWidth = spriteSheet.getWidth() / frameCount;
        //Log.d("frameWidth", Integer.toString(frameWidth));
        int frameHeight = spriteSheet.getHeight();

        // Split the sprite sheet into individual frames
        for (int i = 0; i < frameCount; i++) {
            frames.add(Bitmap.createBitmap(spriteSheet,
                    i * frameWidth, 0, frameWidth, frameHeight));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastFrameChangeTime >= frameDuration) {
            currentFrameIndex = (currentFrameIndex + 1) % frames.size();
            //currentFrameIndex = (currentFrameIndex + 1) % 9;
            lastFrameChangeTime = currentTime;
        }

        setImageBitmap(frames.get(currentFrameIndex));
        super.onDraw(canvas);
        invalidate(); // Redraw the view
    }
}
