package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.toolbar.tools.sanitywarn.alerts

import android.content.Context
import android.util.AttributeSet
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.timer.TimerHandler
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.toolbar.tools.sanitywarn.SanityWarningView

class SanityWarnSetupView : SanityWarningView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        labelView?.text = resources.getString(R.string.investigation_timer_setuplabel)
        thisPhase = TimerHandler.Phase.SETUP
    }

    override var activeCondition: () -> Boolean = {
        currentPhase == TimerHandler.Phase.SETUP
    }

}