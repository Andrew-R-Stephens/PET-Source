package com.tritiumstudios.phasmophobiaevidencetool.android.activities.mainmenus.startscreen.data.animations.graphicsdata;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import androidx.annotation.NonNull;

import com.tritiumstudios.phasmophobiaevidencetool.android.activities.mainmenus.startscreen.data.animations.AbstractAnimatedGraphic;

/**
 * FrostscreenData class
 *
 * @author TritiumGamingStudios
 */
public class AnimatedMirrorData extends AbstractAnimatedGraphic {

    /**
     * @param screenW
     * @param screenH
     */
    public AnimatedMirrorData(int screenW, int screenH) {
        super(screenW, screenH);

        MAX_TICK = 500;
        fadeTick = .7;

        setScale(1);

        setWidth();
        setHeight();

        setX();
        setY();

        setTickMax((int) ((Math.random() * (MAX_TICK - (MAX_TICK * .5))) + (MAX_TICK * .5)));
    }

    @Override
    public void setWidth() {
        setWidth(SCREENW);
    }

    @Override
    public void setHeight() {
        setHeight(SCREENH);
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
        this.x = 0;
        if (getScaledWidth() > SCREENW) {
            this.x -= (getScaledHeight() / 2.0);
        }
    }

    /**
     *
     */
    public void setY() {
        this.y = 0;
        if (getScaledHeight() > SCREENH) {
            this.y -= (getScaledHeight() / 2.0);
        }
    }

    /**
     * @return
     */
    public double getScaledWidth() {
        return scale * width;
    }

    /**
     * @return
     */
    public double getScaledHeight() {
        return scale * height;
    }

    /**
     *
     */
    public void setRect() {
        r.set((int) x, (int) y, (int) (x + getScaledWidth()), (int) (y + getScaledHeight()));
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
        if (currentTick >= MAX_TICK) {
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
                Color.argb(alpha, 255, 255, 255),
                PorterDuff.Mode.MULTIPLY);
    }

}
