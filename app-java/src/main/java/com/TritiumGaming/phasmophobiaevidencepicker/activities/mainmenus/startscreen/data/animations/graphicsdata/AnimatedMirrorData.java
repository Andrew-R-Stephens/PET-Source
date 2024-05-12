package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.AnimatedGraphic;

/**
 * FrostscreenData class
 *
 * @author TritiumGamingStudios
 */
public class AnimatedMirrorData extends AnimatedGraphic {

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

    public void setTickMax(int tickMax) {
        this.MAX_TICK = tickMax;
    }

    public void setX() {
        this.x = 0;
        if (getScaledWidth() > SCREENW) {
            this.x -= (getScaledHeight() / 2.0);
        }
    }

    public void setY() {
        this.y = 0;
        if (getScaledHeight() > SCREENH) {
            this.y -= (getScaledHeight() / 2.0);
        }
    }

    public double getScaledWidth() {
        return scale * width;
    }

    public double getScaledHeight() {
        return scale * height;
    }

    public void setRect() {
        rect.set((int) x, (int) y, (int) (x + getScaledWidth()), (int) (y + getScaledHeight()));
    }

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

    @NonNull
    public PorterDuffColorFilter getFilter() {
        return new PorterDuffColorFilter(
                Color.argb(alpha, 255, 255, 255),
                PorterDuff.Mode.MULTIPLY);
    }

}
