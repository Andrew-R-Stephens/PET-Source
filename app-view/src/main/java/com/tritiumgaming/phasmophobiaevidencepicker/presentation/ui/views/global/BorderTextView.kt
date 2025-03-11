package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.global

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.tritiumgaming.phasmophobiaevidencepicker.R

class BorderTextView : ConstraintLayout {
    constructor(context: Context) :
            super(context) { init() }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { init() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { init() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { init() }

    fun init() {
        inflate(context, R.layout.layout_labeled_textview, this)
    }
}
