package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.utilities.ghostboxutility.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getColorFromAttribute

/**
 * WaveformView class
 *
 * @author TritiumGamingStudios
 */
class WaveformView : View {
    private var mBytes: ByteArray? = null
    private var mPoints: FloatArray? = null

    private val sampleRate = 128.0f
    private val peakAmplitude = .8f

    private var xSpace = 0.0
    private val ySpace = .1

    private val waveformRect = Rect()
    private val frameRect = Rect()

    private val waveformPaint = Paint()
    private val framePaint = Paint()
    private val gridPaint = Paint()
    private val bgPaint = Paint()

    constructor(context: Context?) : super(context) { init() }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { init() }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { init() }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { init() }

    fun init() {
        mBytes = null
        @ColorInt val gridColor = Color.argb(50, 150, 0, 0)
        @ColorInt val backgroundColor = Color.argb(50, 200, 0, 0)

        @ColorInt val waveformColor = getColorFromAttribute(context, R.attr.textColorPrimary)
        @ColorInt val frameColor = getColorFromAttribute(context, R.attr.textColorSecondary)

        waveformPaint.strokeWidth = 5f
        waveformPaint.style = Paint.Style.STROKE
        waveformPaint.isAntiAlias = true
        waveformPaint.color = waveformColor

        framePaint.strokeWidth = 10f
        framePaint.style = Paint.Style.STROKE
        framePaint.isAntiAlias = true
        framePaint.color = frameColor

        gridPaint.strokeWidth = 1f
        gridPaint.style = Paint.Style.STROKE
        gridPaint.isAntiAlias = true
        gridPaint.color = gridColor

        bgPaint.strokeWidth = 1f
        bgPaint.style = Paint.Style.FILL
        bgPaint.isAntiAlias = true
        bgPaint.color = backgroundColor
    }

    fun updateVisualizer(bytes: ByteArray?) {
        this.mBytes = bytes
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        xSpace = ySpace * (height.toDouble() / width.toDouble())

        frameRect[0, 0, width] = height
        waveformRect[0, (frameRect.height() * .2).toInt(), frameRect.width()] =
            (frameRect.height() * .7).toInt()

        canvas.drawRect(frameRect, bgPaint)

        var y = 0
        while (y < frameRect.height()) {
            canvas.drawLine(0f, y.toFloat(), frameRect.width().toFloat(), y.toFloat(), gridPaint)
            y += (frameRect.height() * ySpace).toInt()
        }
        var x = 0
        while (x < frameRect.width()) {
            canvas.drawLine(x.toFloat(), 0f, x.toFloat(), frameRect.height().toFloat(), gridPaint)
            x += (frameRect.width() * xSpace).toInt()
        }

        if (mBytes == null) {
            canvas.drawLine(
                0f, waveformRect.height().toFloat(),
                waveformRect.width().toFloat(), waveformRect.height().toFloat(),
                waveformPaint
            )
            return
        }

        if (mPoints == null || mPoints!!.size < mBytes!!.size * 4) {
            mPoints = FloatArray(mBytes!!.size * 4)
        }

        mPoints?.let { mPoints -> mBytes?.let { mBytes ->
                for (i in 0 until mBytes.size - 1) {
                    mPoints[i * 4] =
                        waveformRect.width().toFloat() * i / (mBytes.size - 1).toFloat()
                    mPoints[i * 4 + 1] =
                        waveformRect.height().toFloat() + ((mBytes[i] + sampleRate).toInt()
                            .toByte()) * (waveformRect.height() * peakAmplitude) / sampleRate
                    mPoints[i * 4 + 2] =
                        waveformRect.width().toFloat() * (i + 1) / (mBytes.size.toFloat() - 1)
                    mPoints[i * 4 + 3] =
                        waveformRect.height().toFloat() + ((mBytes[i + 1] + sampleRate).toInt()
                            .toByte()) * (waveformRect.height() * peakAmplitude) / sampleRate
                }
            }
            canvas.drawLines(mPoints, waveformPaint)
        }

    }
}