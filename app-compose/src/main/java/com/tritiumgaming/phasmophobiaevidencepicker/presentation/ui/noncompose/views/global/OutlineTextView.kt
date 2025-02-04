package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.noncompose.views.global

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.tritiumgaming.phasmophobiaevidencepicker.R

@Deprecated("Migrate to Jetpack Compose")
class OutlineTextView : AppCompatTextView {

    private val OUTLINE_WIDTH_DEFAULT: Float = 0f
    private var isDrawing: Boolean = false
    @ColorInt private var outlineColor: Int = 0
    private var outlineWidth: Float = 0f

    constructor(context: Context) :
            super(context) { init(null) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { init(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { init(attrs) }

    fun init(attrs: AttributeSet?) {
        setDefaults()

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.OutlineTextView)
            outlineColor = a.getColor(
                R.styleable.OutlineTextView_outlineColor, currentTextColor
            )
            outlineWidth = a.getDimension(
                R.styleable.OutlineTextView_outlineWidth, OUTLINE_WIDTH_DEFAULT
            )
            a.recycle()
        }

        setOutlineWidth(TypedValue.COMPLEX_UNIT_PX, outlineWidth)
        setOutlineColor(outlineColor)
    }

    private fun setDefaults() {
        outlineColor = currentTextColor
        outlineWidth = OUTLINE_WIDTH_DEFAULT
    }

    fun setOutlineColor(color: Int) {
        outlineColor = color
    }

    fun setOutlineWidth(unit: Int, width: Float) {
        outlineWidth = TypedValue.applyDimension(
            unit, width, context.resources.displayMetrics
        )
    }

    override fun invalidate() {
        // prevent onDraw.setTextColor force redraw
        if (isDrawing) return

        super.invalidate()
    }

    public override fun onDraw(canvas: Canvas) {
        val paint = paint ?: return

        isDrawing = true

        paint.style = Paint.Style.FILL
        super.onDraw(canvas)

        @ColorInt val currentTextColor = currentTextColor
        drawTextOutline(paint)
        super.onDraw(canvas)
        drawTextForeground(paint, currentTextColor)
        super.onDraw(canvas)

        isDrawing = false
    }

    private fun drawTextOutline(paint: Paint) {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = outlineWidth
        setTextColor(outlineColor)
    }

    private fun drawTextForeground(paint: Paint, @ColorInt textColor: Int) {
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 0f
        setTextColor(textColor)
    }
}
