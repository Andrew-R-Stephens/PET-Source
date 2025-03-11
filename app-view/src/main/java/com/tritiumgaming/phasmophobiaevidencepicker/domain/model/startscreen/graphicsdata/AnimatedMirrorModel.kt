package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.startscreen.graphicsdata

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.startscreen.AAnimatedModel

/**
 * FrostscreenData class
 *
 * @author TritiumGamingStudios
 */
class AnimatedMirrorModel(
    screenW: Int, screenH: Int
) : AAnimatedModel(
    screenW, screenH
) {

    override val filter: PorterDuffColorFilter
        get() = PorterDuffColorFilter(
            Color.argb(alpha, 255, 255, 255),
            PorterDuff.Mode.MULTIPLY
        )

    val scaledWidth: Double
        get() = scale * width

    val scaledHeight: Double
        get() = scale * height

    init {
        MAX_TICK = 500
        fadeTick = .7

        scale = 1.0

        setWidth()
        setHeight()

        setX()
        setY()

        setTickMax(((Math.random() * (MAX_TICK - (MAX_TICK * .5))) + (MAX_TICK * .5)).toInt())
    }

    override fun setWidth() {
        width = SCREENW.toDouble()
    }

    override fun setHeight() {
        height = SCREENH.toDouble()
    }

    fun setTickMax(tickMax: Int) {
        this.MAX_TICK = tickMax
    }

    fun setX() {
        this.x = 0.0
        if (scaledWidth > SCREENW) {
            this.x -= (scaledHeight / 2.0)
        }
    }

    fun setY() {
        this.y = 0.0
        if (scaledHeight > SCREENH) {
            this.y -= (scaledHeight / 2.0)
        }
    }

    override fun setRect() {
        rect[x.toInt(), y.toInt(), (x + scaledWidth).toInt()] = (y + scaledHeight).toInt()
    }

    override fun doTick() {
        setRect()
        if (currentTick >= 0) {
            currentTick += tickIncrementDirection
        } else {
            isAlive = false
        }
        if (currentTick >= MAX_TICK) {
            tickIncrementDirection *= -1
        }
        setAlpha()
    }

}
