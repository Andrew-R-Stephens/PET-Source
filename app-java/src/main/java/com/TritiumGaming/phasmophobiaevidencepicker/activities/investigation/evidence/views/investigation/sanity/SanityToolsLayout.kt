package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.carousel.DifficultyCarouselLayout
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.carousel.MapCarouselLayout
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.controller.SanityTrackerLayout
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.sanitywarn.SanityWarningLayout
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.timer.PhaseTimerLayout
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel

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

    fun init(investigationViewModel: InvestigationViewModel) {
        sanityWarningLayout.init(investigationViewModel)
        phaseTimerLayout.init(investigationViewModel)
        sanityTrackerView.init(investigationViewModel)

        mapCarouselLayout.init(investigationViewModel)
        difficultyCarouselLayout.init(investigationViewModel)
    }
}
