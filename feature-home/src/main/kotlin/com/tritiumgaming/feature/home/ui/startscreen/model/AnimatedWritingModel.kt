package com.tritiumgaming.feature.home.ui.startscreen.model

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter

/**
 * GhostWritingData class
 *
 * @author TritiumGamingStudios
 */
class AnimatedWritingModel(
    screenWidth: Int, screenHeight: Int,
    private val bitmapW: Int, private val bitmapH: Int,
    animationData: AnimationModel
) : AAnimatedModel(
    screenWidth, screenHeight
) {

    override val filter: PorterDuffColorFilter
        get() = PorterDuffColorFilter(
            Color.argb(currentAlpha, 100, 100, 100),
            PorterDuff.Mode.MULTIPLY
        )

    val scaledWidth: Double
        get() = drawWidth

    val scaledHeight: Double
        get() = drawHeight

    init {
        alphaMax = 200
        sizeMax = 1
        sizeMin = 1
        rotationMax = 45
        tickMax = 500

        drawScale = 1.0
        drawRotation = (Math.random() * (rotationMax * 2) - rotationMax).toFloat()

        setWidth()
        setHeight()

        setX()
        setY()

        tickMax =
            ((Math.random() * (this.tickMax - (this.tickMax * .5))) + (this.tickMax * .5)).toInt()

        animationData.rotWriting = drawRotation
    }

    override fun setWidth() {
        drawWidth = bitmapW.toDouble()
    }

    override fun setHeight() {
        drawHeight= bitmapH.toDouble()
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

    override fun setRect() {
        rect[drawX.toInt(), drawY.toInt(), (drawX + scaledWidth).toInt()] =
            (drawY + scaledHeight).toInt()
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
