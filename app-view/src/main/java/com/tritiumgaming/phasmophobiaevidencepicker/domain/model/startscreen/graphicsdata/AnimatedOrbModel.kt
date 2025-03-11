package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.startscreen.graphicsdata

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.startscreen.AAnimatedModel

/**
 * GhostOrbData class
 *
 * @author TritiumGamingStudios
 */
class AnimatedOrbModel(
    screenW: Int, screenH: Int
) : AAnimatedModel(
    screenW, screenH
) {

    override val filter: PorterDuffColorFilter
        get() = PorterDuffColorFilter(
            Color.argb(alpha, 255, 255, 255),
            PorterDuff.Mode.MULTIPLY)

    private val SIZE = 30
    private var ANIM_TICK_MAX = 250
    private var DEST_TICK_MAX = 500

    private var destX = 500f
    private var destY = 500f
    private var animTick = 0
    private var animDir = 1
    private var destTick = 0

    private var velX = 0f
    private var velY = 0f

    init {
        setX()
        setY()

        setDest()

        ANIM_TICK_MAX = getRandTickMax((ANIM_TICK_MAX * .5).toInt(), ANIM_TICK_MAX)
        DEST_TICK_MAX = getRandTickMax((DEST_TICK_MAX * .5).toInt(), DEST_TICK_MAX)
    }

    override fun setWidth() {}

    override fun setHeight() {}

    private fun setX() {
        x = (Math.random() * SCREENW).toFloat().toDouble()
    }

    private fun setY() {
        y = (Math.random() * SCREENH).toFloat().toDouble()
    }

    fun setDest() {
        destX = Math.random().toFloat() * SCREENW
        destY = Math.random().toFloat() * SCREENH
    }

    fun getRandTickMax(min: Int, max: Int): Int {
        return ((Math.random() * (max - min)) + min).toInt()
    }

    private fun calcSlope(from: Float, dest: Float): Float {
        return dest - from
    }

    override fun doTick() {
        animTick += animDir
        if (animTick >= ANIM_TICK_MAX) {
            animDir = -1
        }
        if (animTick < 0) {
            isAlive = false
        }

        destTick++
        if (destTick >= DEST_TICK_MAX) {
            getRandTickMax((DEST_TICK_MAX * .5).toInt(), DEST_TICK_MAX)
            destTick = 0
            setDest()
        }

        val slopeX = calcSlope(x.toInt().toFloat(), destX)
        val slopeY = calcSlope(y.toInt().toFloat(), destY)

        val speed = .1f
        val mult = .001f
        velX += (slopeX * mult * speed)
        velY += (slopeY * mult * speed)

        val maxVel = Math.PI.toFloat() / 2f
        val minVel = -1 * maxVel
        if (velX > maxVel) {
            velX = maxVel
        } else if (velX < minVel) {
            velX = minVel
        }

        if (velY > maxVel) {
            velY = maxVel
        } else if (velY < minVel) {
            velY = minVel
        }

        x += velX.toDouble()
        y += velY.toDouble()

        if (x > SCREENW * 1.1f) {
            x = (SCREENW * 1.1f).toDouble()
            velX = 0f
            setDest()
        } else if (x < (SCREENW * -.1f) - SIZE) {
            x = ((SCREENW * -.1f) - SIZE).toDouble()
            velX = 0f
            setDest()
        }

        if (y > SCREENH * 1.1f) {
            y = (SCREENH * 1.1f).toDouble()
            velY = 0f
            setDest()
        } else if (y < (SCREENH * -.1f) - SIZE) {
            y = ((SCREENH * -.1f) - SIZE).toDouble()
            velY = 0f
            setDest()
        }

        rect[x.toInt(), y.toInt(), (x + SIZE).toInt()] = (y + SIZE).toInt()

        setAlpha()
    }

    override fun setAlpha() {
        val alphaMult = animTick.toDouble() / ANIM_TICK_MAX.toDouble() / fadeTick * MAX_ALPHA
        alpha = alphaMult.toInt()
        if (alpha > MAX_ALPHA) {
            alpha = MAX_ALPHA
        } else if (alpha < 0) {
            alpha = 0
        }
    }

    override fun setRect() {}
}
