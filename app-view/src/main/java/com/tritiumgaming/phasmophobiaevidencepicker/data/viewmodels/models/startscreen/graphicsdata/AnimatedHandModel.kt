package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.models.startscreen.graphicsdata

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.models.startscreen.AAnimatedModel
import com.tritiumgaming.phasmophobiaevidencepicker.utils.BitmapUtils

/**
 * HandprintData class
 *
 * @author TritiumGamingStudios
 */
class AnimatedHandModel(
    screenW: Int, screenH: Int,
    bitmapW: Int, bitmapH: Int
) : AAnimatedModel(
    screenW, screenH
) {

    override val filter: PorterDuffColorFilter
    get() = PorterDuffColorFilter(
        Color.argb(alpha, 0, 255, 0),
        PorterDuff.Mode.MULTIPLY
    )

    private var bitmapW = 0
    private var bitmapH = 0

    val scaledWidth: Double
        get() = width

    val scaledHeight: Double
        get() = height

    init {
        MAX_SIZE = 6
        MIN_SIZE = 3
        MAX_ROTATION = 25
        MAX_TICK = 500

        scale = 1.0

        width = bitmapW.also { this.bitmapW = it }.toDouble()
        height = bitmapH.also { this.bitmapH = it }.toDouble()

        x = Math.random() * SCREENW
        y = Math.random() * SCREENH

        rotation = (Math.random() * (MAX_ROTATION * 2) - MAX_ROTATION).toFloat()
        setTickMax(((Math.random() * (MAX_TICK - (MAX_TICK * .5))) + (MAX_TICK * .5)).toInt())
    }

    fun setX() {
        this.x = Math.random() * SCREENW
        if (this.x + this.scaledWidth > SCREENW) {
            this.x -= this.scaledWidth
        } else if (this.x < this.scaledWidth * -1) {
            this.x = 0.0
        }
    }

    fun setY() {
        this.y = Math.random() * SCREENH
        if (this.y + this.scaledHeight > SCREENH) {
            this.y -= this.scaledHeight
        } else if (this.y < this.scaledHeight * -1) {
            this.y = 0.0
        }
    }

    override fun setWidth() {
        width = bitmapW.toDouble()
    }

    override fun setHeight() {
        height = bitmapH.toDouble()
    }

    fun setTickMax(tickMax: Int) {
        MAX_TICK = tickMax
    }

    override fun setRect() {
        rect[x.toInt(), y.toInt(), (x + scaledWidth).toInt()] = (y + scaledHeight).toInt()
    }

    @Throws(IllegalStateException::class, NullPointerException::class)
    override fun rotateBitmap(original: Bitmap?): Bitmap? {
        if (!BitmapUtils.bitmapExists(original)) return null

        val width = original!!.width
        val height = original.height
        val matrix = Matrix()
        matrix.preRotate(rotation)

        return Bitmap.createBitmap(original, 0, 0, width, height, matrix, true)
    }

    override fun doTick() {
        setRect()
        if (currentTick >= 0) {
            currentTick += tickIncrementDirection
        } else {
            isAlive = false
        }
        if (currentTick >= this.MAX_TICK) {
            tickIncrementDirection *= -1
        }
        setAlpha()
    }

}
