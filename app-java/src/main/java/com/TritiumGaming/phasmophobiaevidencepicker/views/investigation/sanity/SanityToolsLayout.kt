package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.carousel.DifficultyCarouselLayout
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.carousel.MapCarouselLayout
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.timer.PhaseTimerLayout
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.controller.SanityTrackerLayout
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.sanitywarn.SanityWarningLayout

class SanityToolsLayout : ConstraintLayout {

    private lateinit var sanityWarningLayout: SanityWarningLayout
    private lateinit var phaseTimerLayout: PhaseTimerLayout
    private lateinit var sanityTrackerView: SanityTrackerLayout

    private lateinit var mapCarouselLayout: MapCarouselLayout
    private lateinit var difficultyCarouselLayout: DifficultyCarouselLayout

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

        sanityWarningLayout = findViewById(R.id.sanityWarningLayout)
        phaseTimerLayout = findViewById(R.id.phaseTimerLayout)
        sanityTrackerView = findViewById(R.id.sanityTrackerView)

        mapCarouselLayout = findViewById(R.id.mapCarouselLayout)
        difficultyCarouselLayout = findViewById(R.id.difficultyCarouselLayout)

        setDefaults()
    }

    private fun setDefaults() {
    }

    fun init(evidenceViewModel: EvidenceViewModel) {
        sanityWarningLayout.init(evidenceViewModel)
        phaseTimerLayout.init(evidenceViewModel)
        sanityTrackerView.init(evidenceViewModel)

        mapCarouselLayout.init(evidenceViewModel)
        difficultyCarouselLayout.init(evidenceViewModel)
    }
}
