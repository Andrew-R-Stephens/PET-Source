package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.BitmapUtils;

/**
 * Animated class
 *
 * @author TritiumGamingStudios
 */
public abstract class AbstractAnimatedGraphic {

    protected final Rect r = new Rect();

    protected int SCREENW, SCREENH;
    protected int MAX_ALPHA = 200,
            MIN_SIZE = 2, MAX_SIZE = 3,
            MAX_ROTATION = 45, MAX_TICK = 100;

    protected int alpha = 0, tickIncrementDirection = 1, currentTick = 0;
    protected double fadeTick = .2,
            x, y,
            width, height,
            scale = 1;
    protected float rotation = 1;
    protected boolean isAlive = true;

    /**
     * @param screenW
     * @param screenH
     */
    public AbstractAnimatedGraphic(int screenW, int screenH) {
        setScreenW(screenW);
        setScreenH(screenH);
    }

    /**
     * @param screenW
     */
    public void setScreenW(int screenW) {
        SCREENW = screenW;
    }

    /**
     * @param screenH
     */
    public void setScreenH(int screenH) {
        SCREENH = screenH;
    }

    /**
     * @param scale
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setWidth(double w) {
        width = w;
    }

    public void setHeight(double h) {
        height = h;
    }

    /**
     * @param rot
     */
    public void setRotation(float rot) {
        rotation = rot;
    }

    @Nullable
    public Bitmap rotateBitmap(Bitmap original) throws IllegalStateException, NullPointerException {
        return original;
    }

    /**
     * @return
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * @return
     */
    @NonNull
    public Rect getRect() {
        return r;
    }

    /**
     *
     */
    public void setAlpha() {
        double alphaMult = (double) currentTick / (double) MAX_TICK / fadeTick * MAX_ALPHA;
        alpha = (int) alphaMult;
        if (alpha > MAX_ALPHA) {
            alpha = MAX_ALPHA;
        }
        else if (alpha < 0) {
            alpha = 0;
        }
    }

    /**
     * @return
     */
    public abstract PorterDuffColorFilter getFilter();

    /**
     * @param canvas
     * @param paint
     * @param bitmap
     */
    public void draw(@Nullable Canvas canvas, Paint paint, Bitmap bitmap) {
        if (BitmapUtils.bitmapExists(bitmap) && canvas != null) {
            canvas.drawBitmap(
                    bitmap, null, getRect(), paint);
        }
    }

    /**
     * @param canvas
     * @param paint
     * @param bitmap1
     * @param bitmap2
     */
    public void draw(@Nullable Canvas canvas, Paint paint, Bitmap bitmap1, Bitmap bitmap2) {

        if (BitmapUtils.bitmapExists(bitmap1) && canvas != null) {

            canvas.drawBitmap(
                    bitmap1, null, getRect(), paint);

            canvas.drawBitmap(
                    bitmap2, null, getRect(), paint);
        }
    }

    public abstract void setWidth();

    public abstract void setHeight();

    public abstract void tick();

    public abstract void setRect();

    public void initDims(int screenW, int screenH) {
        setScreenW(screenW);
        setScreenH(screenH);

        setWidth();
        setHeight();

        setRect();
    }
}
