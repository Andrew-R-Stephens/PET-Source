package com.TritiumGaming.phasmophobiaevidencepicker.data.models.carousels

import android.content.Context
import android.content.res.Resources
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.SanityModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DifficultyCarouselModel(
    context: Context,
    val evidenceViewModel: EvidenceViewModel
) {

    data class DifficultyData(val name: String = "NA", val time: Long)

    enum class Difficulty { AMATEUR, INTERMEDIATE, PROFESSIONAL, NIGHTMARE, INSANITY }

    /* List */
    private var itemList = mutableListOf<DifficultyData>()
    private val itemCount: Int
        get() = itemList.size
    /* -- */

    /* Index */
    private val _currentIndex: MutableStateFlow<Int> =
        MutableStateFlow(Difficulty.AMATEUR.ordinal)
    val currentIndex = _currentIndex.asStateFlow()
    private fun setIndex(index: Int) {
        _currentIndex.value = index
        evidenceViewModel.timerModel?.setTimeRemaining(currentTime)
        evidenceViewModel.timerModel?.resetTimer()
    }
    fun incrementIndex() {
        var i = currentIndex.value + 1
        if (i >= itemCount) { i = 0 }

        setIndex(i)
        evidenceViewModel.sanityModel?.warnTriggered = false
    }
    fun decrementIndex() {
        var i = currentIndex.value - 1
        if (i < 0) { i = itemCount - 1 }

        setIndex(i)
        evidenceViewModel.sanityModel?.warnTriggered = false
    }
    /* -- */

    val currentName: String
        get() = itemList[currentIndex.value].name

    val currentTime: Long
        get() = itemList[currentIndex.value].time

    val responseTypeKnown: Boolean
        get() = currentIndex.value < 2

    /** Defaults if the selected index is out of range of available indexes.
     * @return the difficulty rate multiplier. 1 - default. 0-2 Depending on Map Size. */
    val currentModifier: Float
        get() {
            val diffIndex =
                evidenceViewModel.difficultyCarouselData?.currentIndex?.value ?: return 1f
            if (diffIndex >= 0 && diffIndex < SanityModel.DIFFICULTY_MODIFIER.size) {
                return SanityModel.DIFFICULTY_MODIFIER[diffIndex]
            }
            return 1f
        }

    fun isDifficulty(difficultyIndex: Int): Boolean {
        return this.currentIndex.value == difficultyIndex
    }

    private fun setList(allNames: MutableList<String>, allTimes: MutableList<Long>) {
        if (allNames.size == allTimes.size) {
            for (i in allNames.indices) {
                itemList.add(i, DifficultyData(allNames[i], allTimes[i]))
            }
        }
    }

    init {
        var namesList: MutableList<String> = mutableListOf()
        try {
            namesList = context.resources
                .getStringArray(R.array.evidence_timer_difficulty_names_array).toMutableList()
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }

        val timesList: MutableList<Long> = mutableListOf()
        try {
            val timesListOut = context.resources
                .getStringArray(R.array.evidence_timer_difficulty_times_array)
            timesListOut.forEachIndexed { index, it ->
                timesList.add(index, it.toLong())
            }
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }

        setList(namesList, timesList)
    }
}
