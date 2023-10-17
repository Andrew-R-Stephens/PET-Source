package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;

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
    protected double fadeTick = .2, x, y, width, height, scale = 1;
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

    /**
     * @param w
     */
    public void setWidth(double w) {
        this.width = w;
    }

    /**
     * @param h
     */
    public void setHeight(double h) {
        this.height = h;
    }

    /**
     * @param rot
     */
    public void setRotation(float rot) {
        rotation = rot;
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
    public Rect getRect() {
        return r;
    }

    /**
     *
     */
    public abstract void tick();

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
    public void draw(Canvas canvas, Paint paint, Bitmap bitmap) {
        if (BitmapUtils.bitmapExists(bitmap) && canvas != null) {
            canvas.drawBitmap(
                    bitmap, null, getRect(), paint);
        }
    }

    public void setRect() {
    }

    public void initDims(int screenW, int screenH) {
        setScreenW(screenW);
        setScreenH(screenH);
        setRect();
    }
}
