package com.TritiumGaming.phasmophobiaevidencepicker.data.animations;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

/**
 * FrostscreenData class
 *
 * @author TritiumGamingStudios
 */
public class FrostscreenData extends Animated {
    /**
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
     *
     * @param tickMax
     */
    public void setTickMax(int tickMax){
        this.MAX_TICK = tickMax;
    }

    /**
     *
     */
    public void setX(){
        this.x = 0;
        if(getScaledWidth() > SCREENW )
            this.x -= (getScaledHeight()/2.0);
    }

    /**
     *
     */
    public void setY(){
        this.y = 0;
        if(getScaledHeight() > SCREENH )
            this.y -= (getScaledHeight()/2.0);
    }

    /**
     *
     * @return
     */
    public double getScaledWidth(){
        return scale * width;
    }

    /**
     *
     * @return
     */
    public double getScaledHeight(){
        return scale * height;
    }

    /**
     *
     */
    public void setRect(){
        r.set((int)x, (int)y, (int)(x+getScaledWidth()), (int)(y+getScaledHeight()));
    }

    /**
     *
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
     *
     * @return
     */
    public PorterDuffColorFilter getFilter(){
        return new PorterDuffColorFilter(Color.argb(alpha, 230, 255, 255), PorterDuff.Mode.MULTIPLY);
    }

}
