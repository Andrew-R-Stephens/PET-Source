package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views

import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.carousels.DifficultyCarouselModel
import com.TritiumGaming.phasmophobiaevidencepicker.listeners.CompositeListener
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.SanitySeekBarView
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.sanitywarn.SanityWarningView

/**
 * DifficultySelectControl class
 *
 * @author TritiumGamingStudios
 */
class DifficultyCarouselView {
    private var difficultyCarouselData: DifficultyCarouselModel? = null

    private var timerView: PhaseTimerView? = null
    private var timerControlView: PhaseTimerControlView? = null
    private var difficultyNameView: AppCompatTextView? = null
    private var sanityProgressBar: SanitySeekBarView? = null
    private var warnTextView_warn: SanityWarningView? = null
    private var warnTextView_setup: SanityWarningView? = null
    private var warnTextView_action: SanityWarningView? = null

    private var compositeListenerPrev: CompositeListener? = null
    private var compositeListenerNext: CompositeListener? = null


    /**
     * DifficultySelectControl parameterized constructor
     */
    fun init(
        difficultyCarouselData: DifficultyCarouselModel?,
        timerView: PhaseTimerView?,
        timerControlView: PhaseTimerControlView?,
        prevButton: AppCompatImageButton,
        nextButton: AppCompatImageButton,
        difficultyNameView: AppCompatTextView?,
        warnTextView_warn: SanityWarningView?,
        warnTextView_setup: SanityWarningView?,
        warnTextView_action: SanityWarningView?,
        sanityProgressBar: SanitySeekBarView?
    ) {
        this.difficultyCarouselData = difficultyCarouselData

        this.timerControlView = timerControlView
        this.difficultyNameView = difficultyNameView
        this.timerView = timerView
        this.sanityProgressBar = sanityProgressBar
        this.warnTextView_warn = warnTextView_warn
        this.warnTextView_setup = warnTextView_setup
        this.warnTextView_action = warnTextView_action

        setPrev(prevButton)
        setNext(nextButton)
    }

    private fun setPrev(prev: AppCompatImageButton) {
        prev.setOnClickListener { v: View? ->
            if (difficultyCarouselData!!.decrementDifficulty()) {
                updateRelatedComponents()
                compositeListenerPrev!!.onClick(v!!)
            }
        }
    }

    /**
     * setNext method
     *
     * @param next
     */
    private fun setNext(next: AppCompatImageButton) {
        next.setOnClickListener { v: View? ->
            if (difficultyCarouselData!!.incrementDifficulty()) {
                updateRelatedComponents()
                compositeListenerNext!!.onClick(v!!)
            }
        }
    }

    private fun updateRelatedComponents() {
        difficultyCarouselData!!.resetSanityData()
        if (difficultyCarouselData!!.difficultyIndex == 4) {
            difficultyCarouselData!!.evidenceViewModel.sanityData!!.setInsanityActual(25f)
        }
        sanityProgressBar!!.updateProgress()
        difficultyNameView!!.text = difficultyCarouselData!!.currentDifficultyName
        timerControlView!!.pause()
        createTimerView()

        warnTextView_warn!!.reset()
        warnTextView_setup!!.reset()
        warnTextView_action!!.reset()
    }

    /**
     * createTimer method
     */
    private fun createTimerView() {
        timerView!!.createTimer(false, difficultyCarouselData!!.currentDifficultyTime, 1000L)
    }

    var index: Int
        /**
         * getState method
         *
         * @return index of difficulty array
         */
        get() = difficultyCarouselData!!.difficultyIndex
        /**
         * setState method
         *
         * @param state -
         */
        set(state) {
            difficultyCarouselData!!.difficultyIndex = state
            difficultyNameView!!.text = difficultyCarouselData!!.currentDifficultyName
        }

    fun registerListener(
        compositeListenerPrev: CompositeListener?,
        compositeListenerNext: CompositeListener?
    ) {
        this.compositeListenerPrev = compositeListenerPrev
        this.compositeListenerNext = compositeListenerNext
    }

    /**
     * reset method
     */
    fun reset() {
        index = 0
        createTimerView()
    }
}
