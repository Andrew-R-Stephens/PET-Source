package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.investigation.sanity.tools.sanitywarn.alerts

import android.content.Context
import android.util.AttributeSet
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.timer.TimerHandler
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.investigation.sanity.tools.sanitywarn.SanityWarningView

class SanityWarnActionView : SanityWarningView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        labelView?.text = resources.getString(R.string.investigation_timer_actionlabel)
        thisPhase = TimerHandler.Phase.ACTION
    }

    override var activeCondition: () -> Boolean = {
        currentPhase == TimerHandler.Phase.ACTION || currentPhase == TimerHandler.Phase.HUNT
    }

}