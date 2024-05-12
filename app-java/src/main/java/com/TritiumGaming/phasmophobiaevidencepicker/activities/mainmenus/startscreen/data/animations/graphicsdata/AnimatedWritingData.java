package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.Log;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.StartScreenAnimationViewData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.AnimatedGraphic;

/**
 * GhostWritingData class
 *
 * @author TritiumGamingStudios
 */
public class AnimatedWritingData extends AnimatedGraphic {

    private final int bitmapW, bitmapH;

    /**
     * @param screenW
     * @param screenH
     * @param bitmapW
     * @param bitmapH
     * @param animationData
     */
    public AnimatedWritingData(
            int screenW,
            int screenH,
            int bitmapW,
            int bitmapH,
            @NonNull StartScreenAnimationViewData animationData) {
        super(screenW, screenH);

        this.bitmapW = bitmapW;
        this.bitmapH = bitmapH;

        MAX_ALPHA = 200;
        MAX_SIZE = 1;//3;
        MIN_SIZE = 1;//2;
        MAX_ROTATION = 45;
        MAX_TICK = 500;

        setScale(1);
        setRotation((float) (Math.random() * (MAX_ROTATION * 2) - MAX_ROTATION));

        setWidth();
        setHeight();

        setX();
        setY();

        setTickMax(
                (int) ((Math.random() *
                        (this.MAX_TICK - (this.MAX_TICK * .5))) + (this.MAX_TICK * .5)));

        animationData.rotWriting = rotation;
    }

    @Override
    public void setWidth() {
        setWidth(bitmapW);
    }

    @Override
    public void setHeight() {
        setHeight(bitmapH);
    }

    /**
     * @param tickMax
     */
    public void setTickMax(int tickMax) {
        this.MAX_TICK = tickMax;
    }

    /**
     *
     */
    public void setX() {
        this.x = Math.random() * SCREENW;
        if (this.x + getScaledWidth() > SCREENW) {
            this.x -= getScaledWidth();
        }
        else if (this.x < getScaledWidth() * -1) {
            this.x = 0;
        }
    }

    /**
     *
     */
    public void setY() {
        this.y = Math.random() * SCREENH;
        if (this.y + getScaledHeight() > SCREENH) {
            this.y -= getScaledHeight();
        }
        else if (this.y < getScaledHeight() * -1) {
            this.y = 0;
        }
    }

    /**
     * @param w
     */
    public void setWidth(double w) {
        this.width = w;
        Log.d("BitmapImages", "Width: " + width);
    }

    /**
     * @param h
     */
    public void setHeight(double h) {
        this.height = h;
        Log.d("BitmapImages", "Height: " + height);
    }

    /**
     * @param scale
     */
    public void setScale(double scale) {
        this.scale = scale;
        Log.d("BitmapImages", "Scale: " + scale);
    }

    /**
     * @return
     */
    public double getScaledWidth() {
        return /*this.scale* */  width;
    }

    /**
     * @return
     */
    public double getScaledHeight() {
        return /*this.scale * */ height;
    }

    /**
     *
     */
    public void setRect() {
        rect.set((int) x, (int) y, (int) (x + getScaledWidth()), (int) (y + getScaledHeight()));
    }

    /**
     * rotateBitmap
     * <p>
     * Creates a rotated copy of the original Bitmap
     *
     * @param original- original Bitmap
     * @return new rotated Bitmap
     */
    @Override
    public Bitmap rotateBitmap(@NonNull Bitmap original)
            throws IllegalStateException, NullPointerException {
        int width = original.getWidth();
        int height = original.getHeight();
        Matrix matrix = new Matrix();
        matrix.preRotate(rotation);

        return Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);
    }

    /**
     *
     */
    public void tick() {
        setRect();
        if (currentTick >= 0) {
            currentTick += tickIncrementDirection;
        }
        else {
            isAlive = false;
        }
        if (currentTick >= this.MAX_TICK) {
            tickIncrementDirection *= -1;
        }
        setAlpha();
    }

    /**
     * @return
     */
    @NonNull
    public PorterDuffColorFilter getFilter() {
        return new PorterDuffColorFilter(
                Color.argb(alpha, 100, 100, 100),
                PorterDuff.Mode.MULTIPLY);
    }

}
