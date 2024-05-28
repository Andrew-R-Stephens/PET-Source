package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils.getColorFromAttribute
import com.google.android.play.integrity.internal.f

/**
 * WarnTextView class
 *
 * @author TritiumGamingStudios
 */
class SanityWarningView : AppCompatTextView {
    private var state = false
    private var flashOn = false

    private val OFF = 0
    private val INACTIVE = 1
    private val ACTIVE = 2

    @ColorInt private var color_active = 0
    @ColorInt private var color_inactive = 0
    @ColorInt private var color_off = 0

    constructor(context: Context) :
            super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        color_active = getColorFromAttribute(context, R.attr.light_active)
        color_inactive = getColorFromAttribute(context, R.attr.light_inactive)
        color_off = getColorFromAttribute(context, R.attr.light_off)

        setDefaults()
    }

    private fun setDefaults() {
        setBackgroundResource(R.drawable.rect_border)

        background.setLevel(OFF)
        setTextColor(color_off)

        gravity = Gravity.CENTER
        maxLines = 1
        setPaddingDefaults()
    }

    fun toggleTextState(canFlash: Boolean) {
        @ColorInt val color: Int

        if (this.state) {
            flashOn = !flashOn

            if (canFlash && flashOn) {
                color = color_active
            }
            else { color = color_inactive }

        } else { color = color_off }

        setTextColor(color)
    }


    fun setState(state: Boolean) {
        this.state = state

        if (this.state) {
            background.setLevel(ACTIVE)

            if (flashOn) { setTextColor(color_active) }
            else { setTextColor(color_inactive) }

        } else {
            background.setLevel(OFF)
            setTextColor(color_off)
        }
    }

    fun setState(state: Boolean, flashOn: Boolean) {
        this.flashOn = flashOn
        setState(state)
    }

    fun reset() {
        setState(false)
    }

    private fun setPaddingDefaults() {
        setPadding(
            if (paddingLeft == 0) 4 else paddingLeft,
            if (paddingTop == 0) 4 else paddingTop,
            if (paddingRight == 0) 4 else paddingRight,
            if (paddingBottom == 0) 4 else paddingBottom
        )
    }
}
