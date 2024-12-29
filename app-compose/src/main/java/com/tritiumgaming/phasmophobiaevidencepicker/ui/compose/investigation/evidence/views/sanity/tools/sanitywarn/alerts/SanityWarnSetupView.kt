package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.evidence.views.sanity.tools.sanitywarn.alerts

import android.content.Context
import android.util.AttributeSet
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.sanity.timer.PhaseTimerModel
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.evidence.views.sanity.tools.sanitywarn.SanityWarningView

class SanityWarnSetupView : SanityWarningView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        labelView?.text = resources.getString(R.string.investigation_timer_setuplabel)
        thisPhase = PhaseTimerModel.Phase.SETUP
    }

    override var activeCondition: () -> Boolean = {
        currentPhase == PhaseTimerModel.Phase.SETUP
    }

}