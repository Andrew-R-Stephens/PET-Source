package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.views.PETImageButton

class PhaseTimerLayout : ConstraintLayout {

    private var phaseTimerData: com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.PhaseTimerModel? = null

    private var timer: com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.PhaseTimerModel? = null
    private var playToggleButton: AppCompatImageButton? = null

    @DrawableRes private var icon_play = 0
    @DrawableRes private var icon_pause = 0

    var phaseTimerTextView: AppCompatTextView? = null // TIMER VIEW

    constructor(context: Context) :
            super(context) { initView(null) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { initView(attrs) }

    protected fun initView(attrs: AttributeSet?) {
        inflate(context, R.layout.layout_phasetimer, this)

        phaseTimerTextView = findViewById(R.id.evidence_timer_text);

        setDefaults()
    }

    private fun setDefaults() {
        icon_play = R.drawable.icon_control_play
        icon_pause = R.drawable.icon_control_pause
    }

    fun init(
        evidenceViewModel: EvidenceViewModel,
        timer: com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.PhaseTimerModel?,
    ) {
        this.phaseTimerData = evidenceViewModel.phaseTimerData

        setTimer(timer)
        setTextView(findViewById(R.id.timer_play_pause))

        /* LISTENERS */
        val skipButton = findViewById<PETImageButton>(R.id.timer_skip)
        val timerText = findViewById<AppCompatTextView>(R.id.evidence_timer_text)
        skipButton.setOnClickListener {
            //phaseTimerCountdownView.createTimer(false, 0L, 1000L)
            evidenceViewModel.skipSanityToPercent(0, 50, 50)
        }

        checkPaused()
    }

    private fun setTextView(view: AppCompatImageButton) {
        this.playToggleButton = view
        view.setOnClickListener { toggle() }
    }

    fun setTimer(timer: com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.PhaseTimerModel?) {
        this.timer = timer
    }

    fun checkPaused() {
        if (timer != null && phaseTimerData!!.isPaused) {
            playToggleButton!!.setImageResource(icon_play)
        } else {
            play()
        }
    }

    fun pause() {
        playToggleButton!!.setImageResource(icon_play)
        timer!!.pause()
    }

    fun play() {
        playToggleButton!!.setImageResource(icon_pause)
        timer!!.play()
    }

    fun toggle() {
        if (phaseTimerData!!.isPaused) play()
        else pause()
    }

    fun reset() {
        pause()
    }

}
