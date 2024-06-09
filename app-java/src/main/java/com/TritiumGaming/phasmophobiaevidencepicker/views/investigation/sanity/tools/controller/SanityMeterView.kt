package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.SanityModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils.getColorFromAttribute
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils.interpolate
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.VectorUtils.toBitmap

/**
 * SanityMeterView class
 *
 * @author TritiumGamingStudios
 */
class SanityMeterView : View {
    private var sanityData: SanityModel? = null

    private val paint = Paint()

    @ColorInt private var sanityPieStartColor = 0
    @ColorInt private var sanityPieEndColor = 0

    @ColorInt private var sanityHeadBrainColor = 0
    @ColorInt private var sanityHeadSkullColor = 0

    @ColorInt private var sanityBorderColor = 0

    private val containerRect: RectF = RectF()
    private val sanityRect: Rect = Rect()

    @ColorInt
    var themeColor: Int = 0

    @ColorInt
    var sanityTint: Int = 0
    private var filter: ColorFilter? = null

    private var sanityImg_skull: Bitmap? = null
    private var sanityImg_brain: Bitmap? = null
    private var sanityImg_border: Bitmap? = null

    constructor(context: Context?) :
            super(context)

    constructor(context: Context?, attrs: AttributeSet?) :
            super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    fun init(sanityData: SanityModel?) {
        this.sanityData = sanityData

        filter = PorterDuffColorFilter(
            ContextCompat.getColor(context, R.color.white),
            PorterDuff.Mode.MULTIPLY
        )

        buildImages()
        buildColors()

        setDefaults()
    }

    private fun buildColors() {
        themeColor =
            getColorFromAttribute(
                context, R.attr.textColorPrimary
            )
        sanityTint =
            getColorFromAttribute(
                context, R.attr.selectedColor
            )
        sanityPieStartColor =
            getColorFromAttribute(
                context, R.attr.sanityPieStartColor
            )
        sanityPieEndColor =
            getColorFromAttribute(
                context, R.attr.sanityPieEndColor
            )
        sanityHeadBrainColor =
            getColorFromAttribute(
                context, R.attr.sanityHeadBrainColor
            )
        sanityHeadSkullColor =
            getColorFromAttribute(
                context, R.attr.sanityHeadSkullColor
            )
        sanityBorderColor =
            getColorFromAttribute(
                context, R.attr.sanityBorderColor
            )
    }

    private fun setDefaults() {
        setBackgroundColor(resources.getColor(R.color.transparent))
    }

    fun buildImages() {
        sanityImg_skull = toBitmap(context, R.drawable.icon_sanityhead_skull)
        sanityImg_brain = toBitmap(context, R.drawable.icon_sanityhead_brain)
        sanityImg_border = toBitmap(context, R.drawable.icon_sanityhead_border)
    }

    fun hasBuiltImages(): Boolean {
        return sanityImg_skull != null && sanityImg_brain != null
    }

    fun createBitmap(toBitmap: Bitmap?, id: Int): Bitmap? {
        val o = BitmapFactory.Options()
        o.inSampleSize = 2
        o.inJustDecodeBounds = false

        return addLayer(
            toBitmap, BitmapFactory.decodeResource(
                context.resources,
                id, o
            )
        )
    }

    private fun addLayer(baseLayer: Bitmap?, topLayer: Bitmap?): Bitmap? {
        var baseLayer = baseLayer
        if (baseLayer == null && topLayer != null) {
            baseLayer = Bitmap.createBitmap(
                topLayer.width,
                topLayer.height,
                topLayer.config
            )
        }
        if (baseLayer != null) {
            val canvas = Canvas(baseLayer)

            if (topLayer != null) {
                canvas.drawBitmap(topLayer, Matrix(), null)
                topLayer.recycle()
            }
        }
        System.gc()

        return baseLayer
    }

    fun createFilterColor(r: Int, g: Int, b: Int) {
        filter = PorterDuffColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
    }

    fun createFilterColor(@ColorInt color: Int) {
        filter = PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY)
    }

    override fun onDraw(canvas: Canvas) {
        val padding = 4f
        var h = height.toFloat()
        var w = width.toFloat()
        if (width <= height) {
            h *= width.toFloat() / height.toFloat()
        } else {
            w *= height.toFloat() / width.toFloat()
        }
        containerRect?.set(
            ((width * .5) - (w * .5) + padding).toFloat(),
            ((height * .5) - (h * .5) + padding).toFloat(),
            (w + ((width * .5) - (w * .5)) - padding).toFloat(),
            (h + ((height * .5) - (h * .5))).toFloat() - padding
        )

        paint.isAntiAlias = true

        paint.strokeWidth = 1f
        paint.color = ContextCompat.getColor(context, R.color.transparent)
        paint.style = Paint.Style.FILL
        canvas.drawOval(containerRect!!, paint)

        val insanityDegree: Float
        if (sanityData != null) {
            insanityDegree = sanityData!!.insanityDegree

            @ColorInt val sanityColor =
                interpolate(
                    sanityPieStartColor, sanityPieEndColor,
                    sanityData!!.insanityPercent.value
                )
            //createFilterColor(sanityColor);
            paint.color = sanityColor

            //paint.setColorFilter(filter);
            if (sanityData != null) {
                canvas.drawArc(containerRect, 270f, insanityDegree, true, paint)
            }
            paint.setColorFilter(null)
            paint.strokeWidth = 5f
            paint.color = Color.BLACK
            paint.style = Paint.Style.STROKE
            canvas.drawArc(containerRect, 270f, insanityDegree, true, paint)
        }

        sanityRect[padding.toInt(), padding.toInt(), sanityImg_skull!!.width - padding.toInt()] =
            sanityImg_skull!!.height - padding.toInt()

        paint.setColorFilter(null)
        paint.color = sanityHeadSkullColor
        canvas.drawBitmap(sanityImg_skull!!, sanityRect, containerRect, paint)

        paint.setColorFilter(null)
        paint.color = sanityHeadBrainColor
        canvas.drawBitmap(sanityImg_brain!!, sanityRect, containerRect, paint)


        paint.setColorFilter(null)
        paint.color = sanityBorderColor
        canvas.drawBitmap(sanityImg_border!!, sanityRect, containerRect, paint)

        paint.color = sanityBorderColor
        paint.strokeWidth = 5f
        paint.style = Paint.Style.STROKE

        canvas.drawOval(containerRect, paint)

        super.onDraw(canvas)
    }

    fun recycleBitmaps() {
        val scheduleGarbageCollect = (sanityImg_brain != null || sanityImg_skull != null)

        if (sanityImg_brain != null) {
            sanityImg_brain!!.recycle()
            sanityImg_brain = null
        }
        if (sanityImg_skull != null) {
            sanityImg_skull!!.recycle()
            sanityImg_skull = null
        }

        if (scheduleGarbageCollect) {
            System.gc()
        }
    }
}