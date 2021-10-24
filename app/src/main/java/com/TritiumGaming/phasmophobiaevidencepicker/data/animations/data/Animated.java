package com.TritiumGaming.phasmophobiaevidencepicker.data.animations.data;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;

import com.TritiumGaming.phasmophobiaevidencepicker.data.data.BitmapUtils;

/**
 * Animated class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public abstract class Animated {

    protected final Rect r = new Rect();

    protected int SCREENW, SCREENH;
    protected int MAX_ALPHA = 200, MIN_SIZE = 2, MAX_SIZE = 3, MAX_ROTATION = 45, MAX_TICK = 100;

    protected int alpha = 0, tickIncrementDirection = 1, currentTick = 0;
    protected double fadeTick = .2, x, y, width, height, scale = 1;
    protected float rotation = 1;
    protected boolean isAlive = true;

    /**
     * Animated
     *
     * TODO
     *
     * @param screenW
     * @param screenH
     */
    public Animated(int screenW, int screenH){
        setScreenW(screenW);
        setScreenH(screenH);
    }

    /**
     * setScreenW
     *
     * TODO
     *
     * @param screenW
     */
    public void setScreenW(int screenW) {
        SCREENW = screenW;
    }

    /**
     * setScreenH
     *
     * TODO
     *
     * @param screenH
     */
    public void setScreenH(int screenH) {
        SCREENH = screenH;
    }

    /**
     * setScale
     *
     * TODO
     *
     * @param scale
     */
    public void setScale(double scale){
        this.scale = scale;
    }

    /**
     * setWidth
     *
     * TODO
     *
     * @param w
     */
    public void setWidth(double w){
        this.width = w;
    }

    /**
     * setHeight
     *
     * TODO
     *
     * @param h
     */
    public void setHeight(double h){
        this.height = h;
    }

    /**
     * setRotation
     *
     * TODO
     *
     * @param rot
     */
    public void setRotation(float rot){
        rotation = rot;
    }

    /**
     * isAlive
     *
     * TODO
     *
     * @return
     */
    public boolean isAlive(){
        return isAlive;
    }

    /**
     * getRect
     *
     * TODO
     *
     * @return
     */
    public Rect getRect(){
        return r;
    }

    /**
     * tick
     *
     * TODO
     */
    public abstract void tick();

    /**
     * setAlpha
     *
     * TODO
     */
    public void setAlpha(){
        double alphaMult = (double) currentTick /(double) MAX_TICK /fadeTick* MAX_ALPHA;
        alpha = (int)alphaMult;
        if(alpha > MAX_ALPHA)
            alpha = MAX_ALPHA;
        else if(alpha < 0)
            alpha = 0;
    }

    /**
     * getFilter
     *
     * TODO
     *
     * @return
     */
    public abstract PorterDuffColorFilter getFilter();

    /**
     * draw
     *
     * TODO
     *
     * @param canvas
     * @param paint
     * @param bitmap
     */
    public void draw(Canvas canvas, Paint paint, Bitmap bitmap){
        if (BitmapUtils.bitmapExists(bitmap)) {
            canvas.drawBitmap(bitmap, null, getRect(), paint);
        }
    }

}
