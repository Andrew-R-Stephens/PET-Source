package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.timer

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.PETImageButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PhaseTimerLayout : ConstraintLayout {

    private lateinit var evidenceViewModel: EvidenceViewModel

    private lateinit var playToggleButton: PETImageButton
    private lateinit var skipButton: PETImageButton
    private var phaseTimerTextView: AppCompatTextView? = null // TIMER VIEW

    constructor(context: Context) :
            super(context) { initView(null) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { initView(attrs) }

    fun initView(attrs: AttributeSet?) {
        inflate(context, R.layout.layout_phasetimer, this)

        playToggleButton = findViewById(R.id.timer_play_pause)
        phaseTimerTextView = findViewById(R.id.evidence_timer_text)

        setDefaults()
    }

    private fun setDefaults() {
    }

    fun init(
        evidenceViewModel: EvidenceViewModel
    ) {
        this.evidenceViewModel = evidenceViewModel

        playToggleButton.setImageResource(R.drawable.icon_control_toggleplay)
        skipButton = findViewById(R.id.timer_skip)

        playToggleButton.isEnabled = (evidenceViewModel.timerModel?.paused?.value == true)
        playToggleButton.setOnClickListener {
            evidenceViewModel.timerModel?.toggle()
        }

        /* LISTENERS */
        skipButton.setOnClickListener {
            //phaseTimerCountdownView.createTimer(false, 0L, 1000L)
            //evidenceViewModel.skipSanityToPercent(0, 50, 50)
        }

        initObservables()

        phaseTimerTextView?.text = evidenceViewModel.timerModel?.displayText
    }

    private fun initObservables() {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            evidenceViewModel.timerModel?.paused?.collectLatest {
                playToggleButton.isEnabled = it
            }
        }
    }

}
