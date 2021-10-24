package com.TritiumGaming.phasmophobiaevidencepicker.data.animations.data;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;

/**
 * FrostscreenData class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class FrostscreenData extends Animated {
    /**
     * FrostscreenData constructor
     *
     * TODO
     *
     * @param screenW
     * @param screenH
     */
    public FrostscreenData(int screenW, int screenH) {
        super(screenW, screenH);

        MAX_TICK = 1000;

        setScale(1);
        setWidth(SCREENW);
        setHeight(SCREENH);
        setX();
        setY();
        setTickMax((int)((Math.random()*(MAX_TICK-(MAX_TICK*.5)))+(MAX_TICK*.5)));
        fadeTick = .7;
    }

    /**
     * setTickMax
     *
     * TODO
     *
     * @param tickMax
     */
    public void setTickMax(int tickMax){
        this.MAX_TICK = tickMax;
    }

    /**
     * setX
     *
     * TODO
     */
    public void setX(){
        this.x = 0;
        if(getScaledWidth() > SCREENW )
            this.x -= (getScaledHeight()/2.0);
    }

    /**
     * setY
     *
     * TODO
     */
    public void setY(){
        this.y = 0;
        if(getScaledHeight() > SCREENH )
            this.y -= (getScaledHeight()/2.0);
    }

    /**
     * getScaledWidth
     *
     * TODO
     *
     * @return
     */
    public double getScaledWidth(){
        return scale * width;
    }

    /**
     * getScaledHeight
     *
     * TODO
     *
     * @return
     */
    public double getScaledHeight(){
        return scale * height;
    }

    /**
     * setRect
     *
     * TODO
     */
    public void setRect(){
        r.set((int)x, (int)y, (int)(x+getScaledWidth()), (int)(y+getScaledHeight()));
    }

    /**
     * tick
     *
     * TODO
     */
    public void tick(){
        setRect();
        if(currentTick >= 0)
            currentTick += tickIncrementDirection;
        else
            isAlive = false;
        if(currentTick >= MAX_TICK)
            tickIncrementDirection *= -1;
        setAlpha();
    }

    /**
     * getFilter
     *
     * TODO
     *
     * @return
     */
    public PorterDuffColorFilter getFilter(){
        return new PorterDuffColorFilter(Color.argb(alpha, 230, 255, 255), PorterDuff.Mode.MULTIPLY);
    }

}
