package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data

import android.content.Context
import android.content.res.Resources
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel

class DifficultyCarouselData(context: Context, evidenceViewModel: EvidenceViewModel) {
    @JvmField
    val evidenceViewModel: EvidenceViewModel? =
        evidenceViewModel

    private var titles = arrayOfNulls<String>(0)
    private var times = LongArray(0)

    enum class Difficulty {
        AMATEUR, INTERMEDIATE, PROFESSIONAL, NIGHTMARE, INSANITY
    }

    var difficultyIndex: Int = Difficulty.AMATEUR.ordinal

    init {
        try {
            val difficultyNames = context.resources
                .getStringArray(R.array.evidence_timer_difficulty_names_array)
            setTitles(difficultyNames)
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

    fun setTitles(titles: Array<String?>) {
        this.titles = titles
    }

    private fun setTimes(times: Array<String>) {
        val temp = LongArray(times.size)
        for (i in times.indices) temp[i] = times[i].toLong()

        setDifficultyTimes(temp)
    }

    fun setDifficultyTimes(difficultyTimes: LongArray) {
        this.times = difficultyTimes
    }

    val currentDifficultyTime: Long
        get() {
            if (times == null) {
                return 0L
            }

            return times[difficultyIndex]
        }

    val currentDifficultyName: String?
        get() {
            if (titles == null) {
                return "NA"
            }

            return titles[difficultyIndex]
        }

    fun decrementDifficulty(): Boolean {
        if (evidenceViewModel != null && titles != null) {
            var difficultyIndex = difficultyIndex - 1
            if (difficultyIndex < 0) {
                difficultyIndex = titles.size - 1
            }
            this.difficultyIndex = difficultyIndex

            if (evidenceViewModel.hasSanityData()) {
                evidenceViewModel.sanityData!!.setWarningAudioAllowed(true)
            }

            return true
        }

        return false
    }

    fun incrementDifficulty(): Boolean {
        if (evidenceViewModel != null && titles != null) {
            var state = difficultyIndex + 1
            if (state >= titles.size) {
                state = 0
            }
            difficultyIndex = state

            if (evidenceViewModel.hasSanityData()) {
                evidenceViewModel.sanityData!!.setWarningAudioAllowed(true)
            }

            return true
        }

        return false
    }

    val isResponseTypeKnown: Boolean
        get() = difficultyIndex < 2

    fun resetSanityData() {
        evidenceViewModel!!.sanityData!!.reset()
    }

    fun isDifficulty(difficultyIndex: Int): Boolean {
        return this.difficultyIndex == difficultyIndex
    }
}
