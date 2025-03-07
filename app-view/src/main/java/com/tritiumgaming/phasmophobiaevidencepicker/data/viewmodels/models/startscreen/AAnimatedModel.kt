package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.models.startscreen

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import com.tritiumgaming.phasmophobiaevidencepicker.utils.BitmapUtils

/**
 * Animated class
 *
 * @author TritiumGamingStudios
 */
abstract class AAnimatedModel(screenW: Int, screenH: Int) {

    abstract val filter: PorterDuffColorFilter?

    @JvmField
    val rect: Rect = Rect()

    protected var SCREENW: Int = 0
    protected var SCREENH: Int = 0

    @JvmField
    protected var MAX_ALPHA: Int = 200
    @JvmField
    protected var MIN_SIZE: Int = 2
    @JvmField
    protected var MAX_SIZE: Int = 3
    @JvmField
    protected var MAX_ROTATION: Int = 45
    @JvmField
    protected var MAX_TICK: Int = 100

    @JvmField
    protected var alpha: Int = 0
    @JvmField
    protected var tickIncrementDirection: Int = 1
    @JvmField
    protected var currentTick: Int = 0
    @JvmField
    protected var fadeTick: Double = .2

    protected open var x: Double = 0.0
    protected open var y: Double = 0.0

    @JvmField
    protected var width: Double = 0.0
    protected var height: Double = 0.0

    protected var scale: Double = 1.0
    protected var rotation: Float = 1f

    var isAlive: Boolean = true

    init {
        SCREENW = screenW
        SCREENH = screenH
    }

    @Throws(IllegalStateException::class, NullPointerException::class)
    open fun rotateBitmap(original: Bitmap?): Bitmap? {
        return original
    }

    open fun setAlpha() {
        val alphaMult = currentTick.toDouble() / MAX_TICK.toDouble() / fadeTick * MAX_ALPHA
        alpha = alphaMult.toInt()
        if (alpha > MAX_ALPHA) {
            alpha = MAX_ALPHA
        } else if (alpha < 0) {
            alpha = 0
        }
    }

    fun draw(canvas: Canvas?, paint: Paint?, bitmap: Bitmap?) {
        if (BitmapUtils.bitmapExists(bitmap) && canvas != null) {
            canvas.drawBitmap(
                bitmap!!, null, rect, paint
            )
        }
    }

    abstract fun setWidth()

    abstract fun setHeight()

    abstract fun doTick()

    abstract fun setRect()

    fun initDims(screenW: Int, screenH: Int) {
        SCREENW = screenW
        SCREENH = screenH

        setWidth()
        setHeight()

        setRect()
    }
}
