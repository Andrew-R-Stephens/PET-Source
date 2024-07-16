package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.sanitywarn

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.sanitywarn.alerts.SanityWarnActionView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.sanitywarn.alerts.SanityWarnHuntView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.sanitywarn.alerts.SanityWarnSetupView
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel

class SanityWarningLayout : ConstraintLayout {

    private var sanityWarnSetupView: SanityWarnSetupView
    private var sanityWarnActionView: SanityWarnActionView
    private var sanityWarnHuntView: SanityWarnHuntView

    constructor(context: Context) :
            super(context)

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    init {
        inflate(context, R.layout.layout_sanity_tool_warnings, this)

        sanityWarnSetupView = findViewById(R.id.evidence_sanitymeter_phase_setup)
        sanityWarnActionView = findViewById(R.id.evidence_sanitymeter_phase_action)
        sanityWarnHuntView = findViewById(R.id.evidence_sanitymeter_huntwarning)

        setDefaults()
    }

    private fun setDefaults() { }

    fun init(investigationViewModel: InvestigationViewModel) {
        sanityWarnSetupView.init(investigationViewModel)
        sanityWarnActionView.init(investigationViewModel)
        sanityWarnHuntView.init(investigationViewModel)
    }

}
