package com.tritiumgaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.controller

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.views.composables.SanityMeterView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SanityTrackerLayout : ConstraintLayout {

    private var investigationViewModel: InvestigationViewModel? = null

    private var sanitySeekBarView: SanitySeekBarView? = null
    private var sanityPercentTextView: AppCompatTextView? = null
    private var sanityMeterView: ComposeView? = null

    constructor(context: Context) :
            super(context)

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    init {
        inflate(context, R.layout.layout_sanity_tool_control, this)

        sanitySeekBarView = findViewById(R.id.evidence_sanitymeter_seekbar)
        sanityPercentTextView = findViewById(R.id.evidence_sanitymeter_percentage)
        sanityMeterView = findViewById(R.id.evidence_sanitymeter_progressbar)

        setDefaults()
    }

    private fun setDefaults() {
        setPadding(8)
    }

    fun init(investigationViewModel: InvestigationViewModel) {
        this.investigationViewModel = investigationViewModel

        sanityMeterView?.apply {
            setContent { SanityMeterView(investigationViewModel) }
        }

        sanitySeekBarView?.let { sanitySeekBarView ->
            sanitySeekBarView.init(investigationViewModel)
            sanitySeekBarView.onProgressChangedListener = object :
                SanitySeekBarView.OnSanityBarProgressChangedListener() {
                override fun onChange() {
                    sanityPercentTextView?.text = investigationViewModel.sanityModel?.sanityLevelAsPercent()
                }
            }
        }
        sanityPercentTextView?.text = investigationViewModel.sanityModel?.sanityLevelAsPercent()


        initObservables(investigationViewModel)
    }

    private fun initObservables(investigationViewModel: InvestigationViewModel) {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            investigationViewModel.sanityModel?.sanityLevel?.collectLatest {
                sanityPercentTextView?.text = investigationViewModel.sanityModel?.sanityLevelAsPercent()
            }
        }
    }
}
