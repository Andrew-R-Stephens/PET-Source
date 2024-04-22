package com.tritiumstudios.phasmophobiaevidencetool.activities.mainmenus.startscreen.data.animations.graphicsdata;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import androidx.annotation.NonNull;

import com.tritiumstudios.phasmophobiaevidencetool.activities.mainmenus.startscreen.data.animations.AbstractAnimatedGraphic;

/**
 * GhostOrbData class
 *
 * @author TritiumGamingStudios
 */
public class AnimatedOrbData extends AbstractAnimatedGraphic {

    private final int SIZE = 30;
    private int ANIM_TICK_MAX = 250, DEST_TICK_MAX = 500;

    private float destX = 500, destY = 500;
    private int animTick = 0, animDir = 1, destTick = 0;

    private float velX = 0f, velY = 0f;

    /**
     * @param screenW
     * @param screenH
     */
    public AnimatedOrbData(int screenW, int screenH) {
        super(screenW, screenH);

        setX();
        setY();

        setDest();

        ANIM_TICK_MAX = getRandTickMax((int) (ANIM_TICK_MAX * .5), ANIM_TICK_MAX);
        DEST_TICK_MAX = getRandTickMax((int) (DEST_TICK_MAX * .5), DEST_TICK_MAX);
    }

    @Override
    public void setWidth() { }

    @Override
    public void setHeight() { }

    /**
     *
     */
    private void setX() {
        x = (float) (Math.random() * SCREENW);
    }

    /**
     *
     */
    private void setY() {
        y = (float) (Math.random() * SCREENH);
    }

    /**
     *
     */
    public void setDest() {
        destX = (float) Math.random() * SCREENW;
        destY = (float) Math.random() * SCREENH;
    }

    /**
     * @param min
     * @param max
     * @return
     */
    public int getRandTickMax(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * @param from
     * @param dest
     * @return
     */
    private float calcSlope(float from, float dest) {
        return dest - from;
    }

    /**
     *
     */
    public void tick() {
        animTick += animDir;
        if (animTick >= ANIM_TICK_MAX) {
            animDir = -1;
        }
        if (animTick < 0) {
            isAlive = false;
        }

        destTick++;
        if (destTick >= DEST_TICK_MAX) {
            getRandTickMax((int) (DEST_TICK_MAX * .5), DEST_TICK_MAX);
            destTick = 0;
            setDest();
        }

        float slopeX = calcSlope((int) x, destX);
        float slopeY = calcSlope((int) y, destY);

        float speed = .1f;
        float mult = .001f;
        velX += (slopeX * mult * speed);
        velY += (slopeY * mult * speed);

        float VEL_MAX = (float) Math.PI / 2f;
        float VEL_MIN = -1 * VEL_MAX;
        if (velX > VEL_MAX) {
            velX = VEL_MAX;
        }
        else if (velX < VEL_MIN) {
            velX = VEL_MIN;
        }

        if (velY > VEL_MAX) {
            velY = VEL_MAX;
        }
        else if (velY < VEL_MIN) {
            velY = VEL_MIN;
        }

        x += velX;
        y += velY;

        if (x > SCREENW * 1.1f) {
            x = SCREENW * 1.1f;
            velX = 0;
            setDest();
        } else if (x < (SCREENW * -.1f) - SIZE) {
            x = (SCREENW * -.1f) - SIZE;
            velX = 0;
            setDest();
        }

        if (y > SCREENH * 1.1f) {
            y = SCREENH * 1.1f;
            velY = 0;
            setDest();
        } else if (y < (SCREENH * -.1f) - SIZE) {
            y = (SCREENH * -.1f) - SIZE;
            velY = 0;
            setDest();
        }

        r.set((int) x, (int) y, (int) (x + SIZE), (int) (y + SIZE));

        setAlpha();
    }

    /**
     *
     */
    public void setAlpha() {
        double alphaMult = (double) animTick / (double) ANIM_TICK_MAX / fadeTick * MAX_ALPHA;
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
    @NonNull
    public PorterDuffColorFilter getFilter() {
        return new PorterDuffColorFilter(Color.argb(alpha, 255, 255, 255), PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void setRect() { }

}
