package com.TritiumGaming.phasmophobiaevidencepicker.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageButton
import com.TritiumGaming.phasmophobiaevidencepicker.R

class PETImageButton : AppCompatImageButton {
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
        setDefaults()
        if (attrs != null) {
            val a = c.obtainStyledAttributes(attrs, R.styleable.PETImageButton)
            setImageResource(
                a.getResourceId(
                    R.styleable.PETImageButton_PETImageButtonBackground,
                    R.drawable.icon_button_designs
                )
            )
            setImageLevel(
                a.getInt(
                    R.styleable.PETImageButton_PETImageButtonType, 0
                )
            )
            a.recycle()
        }
    }

    private fun setDefaults() {
        setLayoutParams(LinearLayout.LayoutParams(48, 48))
        setBackgroundColor(resources.getColor(R.color.transparent))
        setScaleType(ScaleType.FIT_CENTER)
        setPaddingDefaults()
        setAdjustViewBounds(true)
    }

    private fun setPaddingDefaults() {
        setPadding(
            if (getPaddingLeft() == 0) 8 else getPaddingLeft(),
            if (paddingTop == 0) 8 else paddingTop,
            if (getPaddingRight() == 0) 8 else getPaddingRight(),
            if (paddingBottom == 0) 8 else paddingBottom
        )
    }
}
