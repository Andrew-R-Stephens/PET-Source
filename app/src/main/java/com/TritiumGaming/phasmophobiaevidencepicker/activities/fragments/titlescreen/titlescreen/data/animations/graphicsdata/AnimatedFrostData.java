package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.graphicsdata;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.AbstractAnimatedGraphic;

/**
 * FrostscreenData class
 *
 * @author TritiumGamingStudios
 */
public class AnimatedFrostData extends AbstractAnimatedGraphic {
    /**
     * @param screenW -
     * @param screenH -
     */
    public AnimatedFrostData(int screenW, int screenH) {
        super(screenW, screenH);

        MAX_TICK = 1000;

        setScale(1);
        setWidth(SCREENW);
        setHeight(SCREENH);
        setX();
        setY();
        setTickMax((int) ((Math.random() * (MAX_TICK - (MAX_TICK * .5))) + (MAX_TICK * .5)));
        fadeTick = .7;
    }

    /**
     * @param tickMax -
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
     * @return scaledWidth
     */
    public double getScaledWidth() {
        return scale * width;
    }

    /**
     * @return scaledHeight
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
     * rotateBitmap
     * <p>
     * Creates a rotated copy of the original Bitmap
     *
     * @param original- original Bitmap
     * @return new rotated Bitmap
     */
    public Bitmap rotateBitmap(Bitmap original) {
        int width = original.getWidth();
        int height = original.getHeight();
        Matrix matrix = new Matrix();
        matrix.preRotate(90);

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
        if (currentTick >= MAX_TICK) {
            tickIncrementDirection *= -1;
        }
        setAlpha();
    }

    /**
     * @return a filter
     */
    public PorterDuffColorFilter getFilter() {
        return new PorterDuffColorFilter(
                Color.argb(alpha, 230, 255, 255),
                PorterDuff.Mode.MULTIPLY);
    }

}
