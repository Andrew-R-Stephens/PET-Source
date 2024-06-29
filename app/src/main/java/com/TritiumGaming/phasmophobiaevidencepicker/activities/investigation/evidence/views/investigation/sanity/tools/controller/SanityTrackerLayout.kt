package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.controller

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.SanityMeterView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SanityTrackerLayout : ConstraintLayout {

    private var investigationViewModel: InvestigationViewModel? = null

    private lateinit var sanitySeekBarView: SanitySeekBarView
    private lateinit var sanityPercentTextView: AppCompatTextView
    private lateinit var sanityMeterView: ComposeView

    constructor(context: Context) :
            super(context) { initView(null) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { initView(attrs) }

    private fun initView(attrs: AttributeSet?) {
        inflate(context, R.layout.layout_sanity_control, this)

        sanitySeekBarView = findViewById(R.id.evidence_sanitymeter_seekbar)
        sanityPercentTextView = findViewById(R.id.evidence_sanitymeter_percentage)
        sanityMeterView = findViewById(R.id.evidence_sanitymeter_progressbar)

        setDefaults()
    }

    private fun setDefaults() {
    }

    fun init(investigationViewModel: InvestigationViewModel) {
        this.investigationViewModel = investigationViewModel

        sanityMeterView.apply {
            setContent { SanityMeterView(investigationViewModel) }
        }

        sanitySeekBarView.init(investigationViewModel)
        sanitySeekBarView.onProgressChangedListener = object :
            SanitySeekBarView.OnSanityBarProgressChangedListener() {
            override fun onChange() {
                Log.d("onChange", "${investigationViewModel.sanityModel?.toPercentString()}")
                sanityPercentTextView.text = investigationViewModel.sanityModel?.toPercentString()
            }
        }
        sanityPercentTextView.text = investigationViewModel.sanityModel?.toPercentString()


        initObservables(investigationViewModel)
    }

    private fun initObservables(investigationViewModel: InvestigationViewModel) {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            investigationViewModel.sanityModel?.sanityLevel?.collectLatest {
                sanityPercentTextView.text = investigationViewModel.sanityModel?.toPercentString()
            }
        }
    }
}
