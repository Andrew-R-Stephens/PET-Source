package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views

import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.PhaseTimerModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel

/**
 * TimerPlayControl class
 *
 * @author TritiumGamingStudios
 */
class PhaseTimerControlView {
    private val phaseTimerData: PhaseTimerModel?

    private var timer: PhaseTimerView? = null
    private var view: AppCompatImageButton? = null

    private var icon_play = 0
    private var icon_pause = 0

    constructor(
        evidenceViewModel: EvidenceViewModel,
        timer: PhaseTimerView?,
        play_pause_view: AppCompatImageButton,
        icon_play: Int,
        icon_pause: Int
    ) {
        this.phaseTimerData = evidenceViewModel.phaseTimerData

        setTimer(timer)
        setTextView(play_pause_view)

        setPlayBackgroundResource(icon_play)
        setPauseBackgroundResource(icon_pause)

        play_pause_view.setOnClickListener { v: View? -> toggle() }

        checkPaused()
    }

    constructor(
        phaseTimerData: PhaseTimerModel?,
        timer: PhaseTimerView?,
        play_pause_view: AppCompatImageButton,
        icon_play: Int,
        icon_pause: Int
    ) {
        this.phaseTimerData = phaseTimerData

        setTimer(timer)
        setTextView(play_pause_view)

        setPlayBackgroundResource(icon_play)
        setPauseBackgroundResource(icon_pause)

        play_pause_view.setOnClickListener { v: View? -> toggle() }

        checkPaused()
    }

    private fun setTextView(view: AppCompatImageButton) {
        this.view = view
    }

    fun setTimer(timer: PhaseTimerView?) {
        this.timer = timer
    }

    private fun setPlayBackgroundResource(icon_play: Int) {
        this.icon_play = icon_play
    }

    private fun setPauseBackgroundResource(icon_pause: Int) {
        this.icon_pause = icon_pause
    }

    fun checkPaused() {
        if (timer != null && phaseTimerData!!.isPaused) {
            view!!.setImageResource(icon_play)
        } else {
            play()
        }
    }

    fun pause() {
        view!!.setImageResource(icon_play)
        timer!!.pause()
    }

    fun play() {
        view!!.setImageResource(icon_pause)
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
