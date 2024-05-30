package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.carousels

import android.content.Context
import android.content.res.Resources
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel

class DifficultyCarouselModel(
    context: Context,
    val evidenceViewModel: EvidenceViewModel
) {

    enum class Difficulty {
        AMATEUR, INTERMEDIATE, PROFESSIONAL, NIGHTMARE, INSANITY
    }

    private var titles = arrayOfNulls<String>(0)
    private var times = LongArray(0)

    var difficultyIndex: Int = Difficulty.AMATEUR.ordinal

    val currentDifficultyTime: Long
        get() {
            return times[difficultyIndex]
        }

    val currentDifficultyName: String?
        get() {
            return titles[difficultyIndex]
        }

    val responseTypeKnown: Boolean
        get() = difficultyIndex < 2

    init {
        try {
            val difficultyNames = context.resources
                .getStringArray(R.array.evidence_timer_difficulty_names_array)
            titles = difficultyNames
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }

        try {
            val difficultyTimes = context.resources
                .getStringArray(R.array.evidence_timer_difficulty_times_array)
            setTimes(difficultyTimes)
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }
    }

    private fun setTimes(times: Array<String>) {
        val temp = LongArray(times.size)
        for (i in times.indices) temp[i] = times[i].toLong()

        setTimes(temp)
    }

    private fun setTimes(difficultyTimes: LongArray) {
        this.times = difficultyTimes
    }

    fun decrementDifficulty(): Boolean {
        var difficultyIndex = difficultyIndex - 1
        if (difficultyIndex < 0) {
            difficultyIndex = titles.size - 1
        }
        this.difficultyIndex = difficultyIndex

        if (evidenceViewModel.hasSanityData()) {
            evidenceViewModel.sanityData!!.warningAudioAllowed = true
        }

        return true
    }

    fun incrementDifficulty(): Boolean {
        var state = difficultyIndex + 1
        if (state >= titles.size) {
            state = 0
        }
        difficultyIndex = state

        if (evidenceViewModel.hasSanityData()) {
            evidenceViewModel.sanityData!!.warningAudioAllowed = true
        }

        return true
    }

    fun resetSanityData() {
        evidenceViewModel.sanityData!!.reset()
    }

    fun isDifficulty(difficultyIndex: Int): Boolean {
        return this.difficultyIndex == difficultyIndex
    }
}
