package com.tritiumstudios.phasmophobiaevidencetool.android.activities.mainmenus.startscreen.data.animations.graphicsdata;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tritiumstudios.phasmophobiaevidencetool.android.activities.mainmenus.startscreen.data.animations.AbstractAnimatedGraphic;
import com.tritiumstudios.phasmophobiaevidencetool.android.data.utilities.BitmapUtils;

/**
 * HandprintData class
 *
 * @author TritiumGamingStudios
 */
public class AnimatedHandData extends AbstractAnimatedGraphic {

    private final int bitmapW, bitmapH;

    public AnimatedHandData(int screenW, int screenH, int bitmapW, int bitmapH) {
        super(screenW, screenH);

        MAX_SIZE = 6;
        MIN_SIZE = 3;
        MAX_ROTATION = 25;
        MAX_TICK = 500;

        setScale(1);
        //setScale((Math.random() * (MAX_SIZE - MIN_SIZE) + MIN_SIZE) * .1);
        setWidth(this.bitmapW = bitmapW);
        setHeight(this.bitmapH = bitmapH);
        setX(Math.random() * SCREENW);
        setY(Math.random() * SCREENH);
        setRotation((float) (Math.random() * (MAX_ROTATION * 2) - MAX_ROTATION));
        setTickMax((int) ((Math.random() * (MAX_TICK - (MAX_TICK * .5))) + (MAX_TICK * .5)));
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
        MAX_TICK = tickMax;
    }

    /**
     * @param rot
     */
    public void setRotation(float rot) {
        this.rotation = rot;
    }

    /**
     * @param x
     */
    public void setX(double x) {
        this.x = x;
        if (this.x + getScaledWidth() > SCREENW) {
            this.x -= getScaledWidth();
        }
        else if (this.x < getScaledWidth() * -1) {
            this.x = 0;
        }
    }

    /**
     * @param y
     */
    public void setY(double y) {
        this.y = y;
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
    }

    /**
     * @param h
     */
    public void setHeight(double h) {
        this.height = h;
    }

    /**
     * @param scale
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    /**
     * @return
     */
    public double getScaledWidth() {
        return /*scale * */ width;
    }

    /**
     * @return
     */
    public double getScaledHeight() {
        return /*scale * */ height;
    }

    /**
     *
     */
    public void setRect() {
        r.set((int) x, (int) y, (int) (x + getScaledWidth()), (int) (y + getScaledHeight()));
    }

    /**
     * @param original
     * @return
     */
    @Nullable
    @Override
    public Bitmap rotateBitmap(@NonNull Bitmap original)
            throws IllegalStateException, NullPointerException {

        if(!BitmapUtils.bitmapExists(original))
            return null;

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
                Color.argb(alpha, 0, 255, 0),
                PorterDuff.Mode.MULTIPLY);
    }

}
