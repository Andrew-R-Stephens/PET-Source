package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.AAnimatedModel

/**
 * FrostscreenData class
 *
 * @author TritiumGamingStudios
 */
class AnimatedFrostModel(
    screenW: Int, screenH: Int
) : AAnimatedModel(
    screenW, screenH
) {

    override val filter: PorterDuffColorFilter
        get() = PorterDuffColorFilter(
            Color.argb(alpha, 230, 255, 255),
            PorterDuff.Mode.MULTIPLY
        )

    private val scaledWidth: Double
        get() = scale * width

    private val scaledHeight: Double
        get() = scale * height

    init {
        MAX_TICK = 1000

        scale = 1.0

        width = SCREENW.toDouble()
        height = SCREENH.toDouble()

        setX()
        setY()

        setTickMax(((Math.random() * (MAX_TICK - (MAX_TICK * .5))) + (MAX_TICK * .5)).toInt())

        fadeTick = .7
    }

    fun setTickMax(tickMax: Int) {
        this.MAX_TICK = tickMax
    }

    override fun setWidth() {
        width = SCREENW.toDouble()
    }

    override fun setHeight() {
        height = SCREENH.toDouble()
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

    /**
     * Creates a rotated copy of the original Bitmap
     *
     * @param original- original Bitmap
     * @return new rotated Bitmap
     */
    override fun rotateBitmap(original: Bitmap?): Bitmap? {
        val width = original!!.width
        val height = original.height
        val matrix = Matrix()
        matrix.preRotate(90f)

        return Bitmap.createBitmap(original, 0, 0, width, height, matrix, true)
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
