package com.TritiumGaming.phasmophobiaevidencepicker.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import com.TritiumGaming.phasmophobiaevidencepicker.R

class PETImageButton : AppCompatImageButton {
    constructor(context: Context) :
            super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    fun init(c: Context, attrs: AttributeSet?) {
        setDefaults()

        if (attrs != null) {
            val attrArray = c.obtainStyledAttributes(attrs, R.styleable.PETImageButton)

            setImageResource(
                attrArray.getResourceId(
                    R.styleable.PETImageButton_PETImageButtonBackground,
                    R.drawable.icon_button_designs
                )
            )

            setImageLevel(
                attrArray.getInt(R.styleable.PETImageButton_PETImageButtonType, 0)
            )

            attrArray.recycle()
        }
    }

    private fun setDefaults() {
        layoutParams = LinearLayout.LayoutParams(48, 48)

        setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
        scaleType = ScaleType.FIT_CENTER

        setPaddingDefaults()
        adjustViewBounds = true
    }

    private fun setPaddingDefaults() {
        setPadding(
            if (paddingLeft == 0) 8 else paddingLeft,
            if (paddingTop == 0) 8 else paddingTop,
            if (paddingRight == 0) 8 else paddingRight,
            if (paddingBottom == 0) 8 else paddingBottom
        )
    }
}