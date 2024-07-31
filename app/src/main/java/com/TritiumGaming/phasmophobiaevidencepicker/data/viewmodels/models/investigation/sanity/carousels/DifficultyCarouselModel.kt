package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.carousels

import android.content.Context
import android.content.res.TypedArray
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DifficultyCarouselModel(
    context: Context, val investigationViewModel: InvestigationViewModel
) {

    companion object DifficultyConstraints {
        val MODIFIER = floatArrayOf(1.0f, 1.5f, 2.0f)

        val INSANITY_START = floatArrayOf(100f, 100f, 100f, 100f, 70f)
    }

    data class DifficultyData(val name: Int = 0, val time: Long)

    enum class Difficulty { AMATEUR, INTERMEDIATE, PROFESSIONAL, NIGHTMARE, INSANITY }

    /* List */
    private var itemList = mutableListOf<DifficultyData>()
    private fun setList(allNames: MutableList<Int>, allTimes: MutableList<Long>) {
        if (allNames.size == allTimes.size) {
            for (i in allNames.indices) {
                itemList.add(i, DifficultyData(allNames[i], allTimes[i]))
            }
        }
    }
    private val itemCount: Int
        get() = itemList.size
    /* -- */

    /* Index */
    private val _currentIndex: MutableStateFlow<Int> =
        MutableStateFlow(Difficulty.AMATEUR.ordinal)
    val currentIndex = _currentIndex.asStateFlow()
    private fun setIndex(index: Int) {
        _currentIndex.value = index
        investigationViewModel.timerModel?.let { timerModel ->
            timerModel.setTimeRemaining(currentTime)
            timerModel.resetTimer()
        }
    }
    fun incrementIndex() {
        var i = currentIndex.value + 1
        if (i >= itemCount) { i = 0 }

        setIndex(i)
        investigationViewModel.phaseWarnModel?.audioWarnTriggered = false
    }
    fun decrementIndex() {
        var i = currentIndex.value - 1
        if (i < 0) { i = itemCount - 1 }

        setIndex(i)
        investigationViewModel.phaseWarnModel?.audioWarnTriggered = false
    }

    /* -- */

    val currentDifficulty: Difficulty
        get() = Difficulty.entries[currentIndex.value]

    val currentName: Int
        get() = itemList[currentIndex.value].name
    fun getNameAt(index: Int): Int {
        return itemList[index].name
    }

    val currentTime: Long
        get() = itemList[currentIndex.value].time

    val currentStartSanity: Float
        get() = INSANITY_START[currentIndex.value]

    val responseTypeKnown: Boolean
        get() = currentIndex.value < Difficulty.PROFESSIONAL.ordinal

    /** Defaults if the selected index is out of range of available indexes.
     * @return the difficulty rate multiplier. 1 - default. 0-2 Depending on Map Size. */
    val currentModifier: Float
        get() {
            val diffIndex =
                investigationViewModel.difficultyCarouselModel?.currentIndex?.value ?: return 1f
            if (diffIndex >= 0 && diffIndex < MODIFIER.size) {
                return MODIFIER[diffIndex]
            }
            return 1f
        }

    init {
        val names = mutableListOf<Int>()
        val namesTypedArray: TypedArray =
            context.resources.obtainTypedArray(R.array.evidence_timer_difficulty_names_array)
        for (i in 0 until namesTypedArray.length()) {
            names.add(i, namesTypedArray.getResourceId(i, 0))
        }
        namesTypedArray.recycle()

        val times = mutableListOf<Long>()
        val timesListOut =
            context.resources.getStringArray(R.array.evidence_timer_difficulty_times_array)
        timesListOut.forEachIndexed { index, it ->
            times.add(index, it.toLong())
        }

        setList(names, times)


        /*
        var namesList: MutableList<String> = mutableListOf()
        try {
            namesList = context.resources
                .getStringArray(R.array.evidence_timer_difficulty_names_array).toMutableList()
        } catch (e: Resources.NotFoundException) { e.printStackTrace() }

        val timesList: MutableList<Long> = mutableListOf()
        try {
            val timesListOut = context.resources
                .getStringArray(R.array.evidence_timer_difficulty_times_array)
            timesListOut.forEachIndexed { index, it ->
                timesList.add(index, it.toLong())
            }
        } catch (e: Resources.NotFoundException) { e.printStackTrace() }

        setList(namesList, timesList)
        */
    }
}
