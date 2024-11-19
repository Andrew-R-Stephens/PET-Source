package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.startscreen.graphicsdata

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.startscreen.AAnimatedModel

/**
 * FrostscreenData class
 *
 * @author TritiumGamingStudios
 */
class AnimatedFrostModel(
    screenWidth: Int, screenHeight: Int
) : AAnimatedModel(
    screenWidth, screenHeight
) {

    override val filter: PorterDuffColorFilter
        get() = PorterDuffColorFilter(
            Color.argb(currentAlpha, 230, 255, 255),
            PorterDuff.Mode.MULTIPLY
        )

    private val scaledWidth: Double
        get() = drawScale * drawWidth

    private val scaledHeight: Double
        get() = drawScale * drawHeight

    init {
        tickMax = 1000

        drawScale = 1.0

        drawWidth = this.screenWidth.toDouble()
        drawHeight = this.screenHeight.toDouble()

        setX()
        setY()

        tickMax = ((Math.random() * (tickMax - (tickMax * .5))) + (tickMax * .5)).toInt()

        fadeTick = .7
    }

    override fun setWidth() {
        drawWidth = screenWidth.toDouble()
    }

    override fun setHeight() {
        drawHeight = screenHeight.toDouble()
    }

    fun setX() {
        this.drawX = 0.0
        if (scaledWidth > screenWidth) {
            this.drawX -= (scaledHeight / 2.0)
        }
    }

    fun setY() {
        this.drawY = 0.0
        if (scaledHeight > screenHeight) {
            this.drawY -= (scaledHeight / 2.0)
        }
    }

    override fun setRect() {
        rect[drawX.toInt(), drawY.toInt(), (drawX + scaledWidth).toInt()] = (drawY + scaledHeight).toInt()
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
        if (currentTick >= tickMax) {
            tickIncrementDirection *= -1
        }
        setAlpha()
    }

}
