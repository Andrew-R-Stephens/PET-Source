package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.controller

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R

class SanityControllerLayout : ConstraintLayout {
    constructor(context: Context) :
            super(context) { init(null) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { init(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { init(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { init(attrs) }

    fun init(attrs: AttributeSet?) {
        inflate(context, R.layout.layout_sanity_control, this)

        setDefaults()
    }

    private fun setDefaults() {
    }
}
