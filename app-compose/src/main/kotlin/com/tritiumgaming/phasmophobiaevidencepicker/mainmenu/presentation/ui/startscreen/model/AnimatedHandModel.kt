package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.startscreen.model

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.util.BitmapUtils

/**
 * HandprintData class
 *
 * @author TritiumGamingStudios
 */
class AnimatedHandModel(
    screenWidth: Int, screenHeight: Int,
    bitmapWidth: Int, bitmapHeight: Int
) : AAnimatedModel(
    screenWidth, screenHeight
) {

    override val filter: PorterDuffColorFilter
    get() = PorterDuffColorFilter(
        Color.argb(currentAlpha, 0, 255, 0),
        PorterDuff.Mode.MULTIPLY
    )

    private var bitmapW = 0
    private var bitmapH = 0

    val scaledWidth: Double
        get() = drawWidth

    val scaledHeight: Double
        get() = drawHeight

    init {
        sizeMax = 6
        sizeMin = 3
        rotationMax = 25
        tickMax = 500

        drawScale = 1.0

        drawWidth = bitmapWidth.also { this.bitmapW = it }.toDouble()
        drawHeight = bitmapHeight.also { this.bitmapH = it }.toDouble()

        drawX = Math.random() * this.screenWidth
        drawY = Math.random() * this.screenHeight

        drawRotation = (Math.random() * (rotationMax * 2) - rotationMax).toFloat()
        tickMax = ((Math.random() * (tickMax - (tickMax * .5))) + (tickMax * .5)).toInt()
    }

    fun setX() {
        this.drawX = Math.random() * screenWidth
        if (this.drawX + this.scaledWidth > screenWidth) {
            this.drawX -= this.scaledWidth
        } else if (this.drawX < this.scaledWidth * -1) {
            this.drawX = 0.0
        }
    }

    fun setY() {
        this.drawY = Math.random() * screenHeight
        if (this.drawY + this.scaledHeight > screenHeight) {
            this.drawY -= this.scaledHeight
        } else if (this.drawY < this.scaledHeight * -1) {
            this.drawY = 0.0
        }
    }

    override fun setWidth() {
        drawWidth = bitmapW.toDouble()
    }

    override fun setHeight() {
        drawHeight = bitmapH.toDouble()
    }

    override fun setRect() {
        rect[drawX.toInt(), drawY.toInt(), (drawX + scaledWidth).toInt()] = (drawY + scaledHeight).toInt()
    }

    @Throws(IllegalStateException::class, NullPointerException::class)
    override fun rotateBitmap(original: Bitmap?): Bitmap? {
        if (!BitmapUtils.bitmapExists(original)) return null

        val width = original!!.width
        val height = original.height
        val matrix = Matrix()
        matrix.preRotate(drawRotation)

        return Bitmap.createBitmap(original, 0, 0, width, height, matrix, true)
    }

    override fun doTick() {
        setRect()
        if (currentTick >= 0) {
            currentTick += tickIncrementDirection
        } else {
            isAlive = false
        }
        if (currentTick >= this.tickMax) {
            tickIncrementDirection *= -1
        }
        setAlpha()
    }

}
