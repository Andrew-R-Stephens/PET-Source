package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.PETImageButton

class PhaseTimerLayout : ConstraintLayout {

    private lateinit var evidenceViewModel: EvidenceViewModel

    private var timer: com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.PhaseTimerModel? = null
    private lateinit var playToggleButton: PETImageButton
    private lateinit var skipButton: PETImageButton

    var phaseTimerTextView: AppCompatTextView? = null // TIMER VIEW

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

        phaseTimerTextView = findViewById(R.id.evidence_timer_text);

        setDefaults()
    }

    private fun setDefaults() {
    }

    fun init(
        evidenceViewModel: EvidenceViewModel
    ) {
        this.evidenceViewModel = evidenceViewModel

        setTimer(timer)
        playToggleButton = findViewById(R.id.timer_play_pause)
        playToggleButton.setImageResource(R.drawable.icon_control_toggleplay)
        playToggleButton.setOnClickListener { toggle() }

        /* LISTENERS */
        skipButton = findViewById(R.id.timer_skip)
        skipButton.setOnClickListener {
            //phaseTimerCountdownView.createTimer(false, 0L, 1000L)
            evidenceViewModel.skipSanityToPercent(0, 50, 50)
        }

        checkPaused()
    }

    private fun setTimer(timer: com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.PhaseTimerModel?) {
        this.timer = timer
    }

    private fun checkPaused() {
        if (timer != null && (evidenceViewModel.phaseTimerData?.isPaused == true)) {
            playToggleButton.isEnabled = false
        } else {
            play()
        }
    }

    private fun pause() {
        playToggleButton.isEnabled = true
        timer?.pause()
    }

    fun play() {
        playToggleButton.isEnabled = false
        timer?.play()
    }

    private fun toggle() {
        if (evidenceViewModel.phaseTimerData?.isPaused == true) play()
        else pause()
    }

    fun reset() {
        pause()
    }

}
