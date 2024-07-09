package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.sanitywarn.alerts

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.sanitywarn.SanityWarningView
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.timer.PhaseTimerModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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