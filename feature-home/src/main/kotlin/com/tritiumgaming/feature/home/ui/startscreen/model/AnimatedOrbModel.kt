package com.tritiumgaming.feature.home.ui.startscreen.model

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter

/**
 * GhostOrbData class
 *
 * @author TritiumGamingStudios
 */
class AnimatedOrbModel(
    screenWidth: Int = 0, screenHeight: Int = 0
) : AAnimatedModel(
    screenWidth, screenHeight
) {

    override val filter: PorterDuffColorFilter
        get() = PorterDuffColorFilter(
            Color.argb(currentAlpha, 255, 255, 255),
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
        drawX = (Math.random() * screenWidth).toFloat().toDouble()
    }

    private fun setY() {
        drawY = (Math.random() * screenHeight).toFloat().toDouble()
    }

    fun setDest() {
        destX = Math.random().toFloat() * screenWidth
        destY = Math.random().toFloat() * screenHeight
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

        val slopeX = calcSlope(drawX.toInt().toFloat(), destX)
        val slopeY = calcSlope(drawY.toInt().toFloat(), destY)

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

        drawX += velX.toDouble()
        drawY += velY.toDouble()

        if (drawX > screenWidth * 1.1f) {
            drawX = (screenWidth * 1.1f).toDouble()
            velX = 0f
            setDest()
        } else if (drawX < (screenWidth * -.1f) - SIZE) {
            drawX = ((screenWidth * -.1f) - SIZE).toDouble()
            velX = 0f
            setDest()
        }

        if (drawY > screenHeight * 1.1f) {
            drawY = (screenHeight * 1.1f).toDouble()
            velY = 0f
            setDest()
        } else if (drawY < (screenHeight * -.1f) - SIZE) {
            drawY = ((screenHeight * -.1f) - SIZE).toDouble()
            velY = 0f
            setDest()
        }

        rect[drawX.toInt(), drawY.toInt(), (drawX + SIZE).toInt()] = (drawY + SIZE).toInt()

        setAlpha()
    }

    override fun setAlpha() {
        val alphaMult = animTick.toDouble() / ANIM_TICK_MAX.toDouble() / fadeTick * alphaMax
        currentAlpha = alphaMult.toInt()
        if (currentAlpha > alphaMax) {
            currentAlpha = alphaMax
        } else if (currentAlpha < 0) {
            currentAlpha = 0
        }
    }

    override fun setRect() {}
}
