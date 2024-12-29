package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.investigation.evidence.views.investigation.sanity.tools.sanitywarn.alerts

import android.content.Context
import android.util.AttributeSet
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.sanity.timer.PhaseTimerModel
import com.tritiumgaming.phasmophobiaevidencepicker.ui.investigation.evidence.views.investigation.sanity.tools.sanitywarn.SanityWarningView

class SanityWarnHuntView : SanityWarningView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        labelView?.text = resources.getString(R.string.investigation_sanity_huntwarn)
        thisPhase = PhaseTimerModel.Phase.HUNT
    }

    override var activeCondition: () -> Boolean = {
        currentPhase == PhaseTimerModel.Phase.HUNT
    }

}