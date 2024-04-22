package com.tritiumstudios.phasmophobiaevidencetool.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import com.tritiumstudios.phasmophobiaevidencetool.R

class OutlineTextView : AppCompatTextView {
    private val defaultOutlineWidth = 0f
    private var isDrawing = false
    private var outlineColor = 0
    private var outlineWidth = 0f

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    fun init(c: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val a = c.obtainStyledAttributes(attrs, R.styleable.OutlineTextView)
            outlineColor = a.getColor(R.styleable.OutlineTextView_outlineColor, currentTextColor)
            outlineWidth =
                a.getDimension(R.styleable.OutlineTextView_outlineWidth, defaultOutlineWidth)
            a.recycle()
        } else {
            outlineColor = currentTextColor
            outlineWidth = defaultOutlineWidth
        }
        setOutlineWidth(TypedValue.COMPLEX_UNIT_PX, outlineWidth)
        setOutlineColor(outlineColor)
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
        val currentTextColor = currentTextColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = outlineWidth
        setTextColor(outlineColor)
        super.onDraw(canvas)
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 0f
        setTextColor(currentTextColor)
        super.onDraw(canvas)
        isDrawing = false
    }
}
