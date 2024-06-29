package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.sanitywarn

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute

/**
 * WarnTextView class
 *
 * @author TritiumGamingStudios
 */
abstract class SanityWarningView : AppCompatTextView {

    lateinit var investigationViewModel: InvestigationViewModel

    private companion object SanityState {
        const val OFF = 0
        const val INACTIVE = 1
        const val ACTIVE = 2
    }

    private var state = false
    private var flashOn = false

    @ColorInt private var colorActive = 0
    @ColorInt private var colorInactive = 0
    @ColorInt private var colorOff = 0

    constructor(context: Context) :
            super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        colorActive = getColorFromAttribute(context, R.attr.light_active)
        colorInactive = getColorFromAttribute(context, R.attr.light_inactive)
        colorOff = getColorFromAttribute(context, R.attr.light_off)

        setDefaults()
    }

    private fun setDefaults() {
        setBackgroundResource(R.drawable.rect_border)
        background.setLevel(OFF)

        setTextColor(colorOff)

        gravity = Gravity.CENTER
        maxLines = 1
        setPaddingDefaults()
    }

    fun init(investigationViewModel: InvestigationViewModel) {
        this.investigationViewModel = investigationViewModel

        initObservables()
    }

    abstract fun initObservables()

    fun toggleTextState(canFlash: Boolean) {
        @ColorInt val color: Int

        if (this.state) {
            flashOn = !flashOn

            color = if (canFlash && flashOn) { colorActive }
                else { colorInactive }

        } else { color = colorOff }

        setTextColor(color)
    }


    fun setState(state: Boolean) {
        this.state = state

        if (this.state) {
            background.setLevel(ACTIVE)

            if (flashOn) { setTextColor(colorActive) }
            else { setTextColor(colorInactive) }

        } else {
            background.setLevel(OFF)
            setTextColor(colorOff)
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
