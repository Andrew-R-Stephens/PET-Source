package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R

class PhaseTimerLayout : ConstraintLayout {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr
    ) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    protected fun init(c: Context, attrs: AttributeSet?) {
        inflate(c, R.layout.layout_phasetimer, this)

        setDefaults()
    }

    private fun setDefaults() {
    }
}
