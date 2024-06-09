package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.timer

import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FormatterUtils.millisToTime
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import java.text.DecimalFormat

/**
 * SetupPhaseTimer class
 *
 * @author TritiumGamingStudios
 */
class TimerModel (
    val evidenceViewModel: EvidenceViewModel
) {
    private val sanityData = evidenceViewModel.sanityData
    private val phaseTimerData = evidenceViewModel.phaseTimerData

    private var timer: CountDownTimer? = null
    //private var stateControl: PhaseTimerControlView? = null
    private var timerTextView: AppCompatTextView? = null

    init {
        createTimer(
            true,
            phaseTimerData!!.timeRemaining,
            1000L
        )

        if (!phaseTimerData.isPaused) {
            timer!!.start()
        }
    }

    /**
     * setTimerTextView method
     *
     * @param timerTextView -
     */
    private fun setTimerTextView(timerTextView: AppCompatTextView?) {
        this.timerTextView = timerTextView
    }

    /**
     * createTimer method
     *
     * @param millisInFuture -
     * @param countDownInterval -
     */
    fun createTimer(isFresh: Boolean, millisInFuture: Long, countDownInterval: Long) {
        destroyTimer()

        if (isFresh) {
            if (!sanityData!!.isNewCycle && !sanityData.paused.value) {
                Log.d(
                    "SettingTimeRemaining",
                    evidenceViewModel.difficultyCarouselData?.currentDifficultyTime.toString() +
                            " " + (sanityData.startTime - System.currentTimeMillis())
                )
                phaseTimerData?.timeRemaining =
                    (evidenceViewModel.difficultyCarouselData?.currentDifficultyTime ?: 0) +
                            (sanityData.startTime - System.currentTimeMillis())
            } else {
                Log.d(
                    "SettingTimeRemaining", "Not new Cycle, Not Paused " +
                            evidenceViewModel.difficultyCarouselData?.currentDifficultyTime +
                            " " + (sanityData.startTime - System.currentTimeMillis())
                )
            }
        } else {
            Log.d(
                "SettingTimeRemaining", "Not Fresh " +
                        evidenceViewModel.difficultyCarouselData?.currentDifficultyTime +
                        " " + (sanityData!!.startTime - System.currentTimeMillis())
            )
            phaseTimerData?.timeRemaining = millisInFuture
        }

        timer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                if ((phaseTimerData?.isPaused == false) && millisUntilFinished > -1L) {
                    phaseTimerData.timeRemaining = millisUntilFinished
                    updateText()
                }
            }

            override fun onFinish() {
                /*if (stateControl != null) {
                    stateControl!!.checkPaused()
                }*/
                phaseTimerData?.timeRemaining = 0L
                updateText()
            }
        }

        updateText()
    }

    /**
     * pause method
     */
    fun pause() {
        if (!phaseTimerData!!.isPaused) {
            phaseTimerData.isPaused = true
            destroyTimer()
        }
    }

    /**
     * unPause method
     */
    fun play() {
        if (phaseTimerData!!.isPaused) {
            sanityData?.setProgressManually()
            phaseTimerData.isPaused = false
            createTimer(false, phaseTimerData.timeRemaining, 1000L)
            if (timer != null) {
                Log.d("Timer", "Playing!")
                timer!!.start()
            }
        }
    }

    /**
     * setText method
     */
    fun updateText() {
        var text = String.format(
            "%s:%s",
            DecimalFormat("0").format(0),
            DecimalFormat("00").format(0)
        )

        if (phaseTimerData!!.timeRemaining > 0L) {
            val breakdown = phaseTimerData.timeRemaining / 1000L
            text = millisToTime("%s:%s", breakdown)
        }

        if (timerTextView != null) {
            timerTextView!!.text = text
        }
    }

    fun destroyTimer() {
        if (timer != null) {
            timer!!.cancel()
        }
        timer = null
        Log.d(
            "Timer",
            (phaseTimerData!!.timeRemaining -
                    (sanityData!!.startTime - System.currentTimeMillis())).toString()
        )
    }

    fun reset() {
        createTimer(
            true,
            phaseTimerData!!.timeRemaining,
            1000L
        )

        updateText()
    }
}
