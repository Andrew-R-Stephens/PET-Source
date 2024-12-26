package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.investigation.evidence.views.sanity.tools.sanitywarn.alerts

import android.content.Context
import android.util.AttributeSet
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.sanitywarn.SanityWarningView
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.sanity.timer.PhaseTimerModel

class SanityWarnActionView : SanityWarningView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        labelView?.text = resources.getString(R.string.investigation_timer_actionlabel)
        thisPhase = PhaseTimerModel.Phase.ACTION
    }

    override var activeCondition: () -> Boolean = {
        currentPhase == PhaseTimerModel.Phase.ACTION || currentPhase == PhaseTimerModel.Phase.HUNT
    }

}