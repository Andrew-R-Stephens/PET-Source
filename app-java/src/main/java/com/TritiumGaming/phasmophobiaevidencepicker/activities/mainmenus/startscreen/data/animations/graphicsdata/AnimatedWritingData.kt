package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.StartScreenAnimationViewData
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.AnimatedGraphic

/**
 * GhostWritingData class
 *
 * @author TritiumGamingStudios
 */
class AnimatedWritingData(
    screenW: Int, screenH: Int,
    private val bitmapW: Int, private val bitmapH: Int,
    animationData: StartScreenAnimationViewData
) : AnimatedGraphic(
    screenW, screenH
) {

    override val filter: PorterDuffColorFilter
        get() = PorterDuffColorFilter(
            Color.argb(alpha, 100, 100, 100),
            PorterDuff.Mode.MULTIPLY
        )

    val scaledWidth: Double
        get() = width

    val scaledHeight: Double
        get() = height

    init {
        MAX_ALPHA = 200
        MAX_SIZE = 1
        MIN_SIZE = 1
        MAX_ROTATION = 45
        MAX_TICK = 500

        scale = 1.0
        rotation = (Math.random() * (MAX_ROTATION * 2) - MAX_ROTATION).toFloat()

        setWidth()
        setHeight()

        setX()
        setY()

        setTickMax(
            ((Math.random() *
                    (this.MAX_TICK - (this.MAX_TICK * .5))) + (this.MAX_TICK * .5)).toInt()
        )

        animationData.rotWriting = rotation
    }

    override fun setWidth() {
        width = bitmapW.toDouble()
    }

    override fun setHeight() {
        height= bitmapH.toDouble()
    }

    fun setTickMax(tickMax: Int) {
        this.MAX_TICK = tickMax
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

    override fun setRect() {
        rect[x.toInt(), y.toInt(), (x + scaledWidth).toInt()] = (y + scaledHeight).toInt()
    }

    /**
     * Creates a rotated copy of the original Bitmap
     *
     * @param original- original Bitmap
     * @return new rotated Bitmap
     */
    @Throws(IllegalStateException::class, NullPointerException::class)
    override fun rotateBitmap(original: Bitmap?): Bitmap? {
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
