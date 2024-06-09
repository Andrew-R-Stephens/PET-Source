package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.controller

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SanityTrackerLayout : ConstraintLayout {

    private var evidenceViewModel: EvidenceViewModel? = null

    private lateinit var sanitySeekBarView: SanitySeekBarView
    private lateinit var sanityPercentTextView: AppCompatTextView

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

        setDefaults()
    }

    private fun setDefaults() {
    }

    fun init(evidenceViewModel: EvidenceViewModel) {
        this.evidenceViewModel = evidenceViewModel

        sanitySeekBarView.init(evidenceViewModel)
        sanitySeekBarView.resetProgress()
        sanityPercentTextView.text = evidenceViewModel.sanityData?.toPercentString()

        sanitySeekBarView.onProgressChangedListener = object :
            SanitySeekBarView.OnSanityBarProgressChangedListener() {
            override fun onChange() {
                sanityPercentTextView.text = evidenceViewModel.sanityData?.toPercentString()
            }

            override fun onReset() {
                TODO("Not yet implemented")
            }
        }

        initObservables(evidenceViewModel)
    }

    private fun initObservables(evidenceViewModel: EvidenceViewModel) {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            evidenceViewModel.sanityData?.insanityPercent?.collectLatest {
                sanityPercentTextView.text = evidenceViewModel.sanityData?.toPercentString()
            }
        }
    }
}
