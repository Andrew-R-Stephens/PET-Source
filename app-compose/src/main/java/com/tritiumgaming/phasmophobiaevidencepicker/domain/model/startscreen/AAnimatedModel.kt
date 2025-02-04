package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.startscreen

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import com.tritiumgaming.phasmophobiaevidencepicker.util.BitmapUtils

/**
 * Animated class
 *
 * @author TritiumGamingStudios
 */
abstract class AAnimatedModel(
    protected var screenWidth: Int = 0,
    protected var screenHeight: Int = 0,
) {

    abstract val filter: PorterDuffColorFilter?

    val rect: Rect = Rect()

    protected var alphaMax: Int = 200
    protected var sizeMin: Int = 2
    protected var sizeMax: Int = 3
    protected var rotationMax: Int = 45
    protected var tickMax: Int = 100

    protected var tickIncrementDirection: Int = 1
    protected var currentTick: Int = 0
    protected var currentAlpha: Int = 0
    protected var fadeTick: Double = .2

    protected open var drawX: Double = 0.0
    protected open var drawY: Double = 0.0

    protected var drawWidth: Double = 0.0
    protected var drawHeight: Double = 0.0

    protected var drawScale: Double = 1.0
    protected var drawRotation: Float = 1f

    var isAlive: Boolean = true

    @Throws(IllegalStateException::class, NullPointerException::class)
    open fun rotateBitmap(original: Bitmap?): Bitmap? {
        return original
    }

    open fun setAlpha() {
        val alphaMultiplier = currentTick.toDouble() / tickMax.toDouble() / fadeTick * alphaMax
        currentAlpha = alphaMultiplier.toInt()
        if (currentAlpha > alphaMax) {
            currentAlpha = alphaMax
        } else if (currentAlpha < 0) {
            currentAlpha = 0
        }
    }

    fun draw(canvas: Canvas?, paint: Paint?, bitmap: Bitmap?) {
        if (BitmapUtils.bitmapExists(bitmap) && canvas != null) {
            canvas.drawBitmap(
                bitmap!!, null, rect, paint
            )
        }
    }

    fun initDims(screenW: Int, screenH: Int) {
        screenWidth = screenW
        screenHeight = screenH

        setWidth()
        setHeight()

        setRect()
    }

    abstract fun setWidth()
    abstract fun setHeight()
    abstract fun doTick()
    abstract fun setRect()

}
