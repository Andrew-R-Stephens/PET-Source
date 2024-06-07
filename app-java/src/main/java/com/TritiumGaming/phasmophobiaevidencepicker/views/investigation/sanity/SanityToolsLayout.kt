package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.sanitywarn.SanityWarningLayout

class SanityToolsLayout : ConstraintLayout {

    lateinit var sanityWarningLayout: SanityWarningLayout
    lateinit var phaseTimerLayout: PhaseTimerLayout
    lateinit var sanityTrackerView: SanityTrackerView

    constructor(context: Context) :
            super(context) { initView(null) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { initView( attrs) }

    private fun initView(attrs: AttributeSet?) {
        inflate(context, R.layout.layout_sanity_tools, this)

        setDefaults()
    }

    private fun setDefaults() {
    }

    fun init(evidenceViewModel: EvidenceViewModel) {
        sanityWarningLayout.init(evidenceViewModel)
        phaseTimerLayout.init(evidenceViewModel, null)
        sanityTrackerView.init(evidenceViewModel)
    }
}
