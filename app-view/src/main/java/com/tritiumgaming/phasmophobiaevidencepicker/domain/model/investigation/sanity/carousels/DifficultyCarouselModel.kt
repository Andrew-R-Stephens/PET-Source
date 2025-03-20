package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.carousels

import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.DifficultyRepository.DifficultyConstraints.INSANITY_START
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.DifficultyRepository.DifficultyConstraints.MODIFIER
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.timer.PhaseTimerModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.warning.PhaseWarningModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DifficultyCarouselModel(
    private val difficultyRepository: DifficultyRepository,
    private val timerModel: PhaseTimerModel?,
    private val phaseWarnModel: PhaseWarningModel?
) {

    /* -- */

    /* Index */
    private val _currentIndex: MutableStateFlow<Int> =
        MutableStateFlow(DifficultyRepository.DifficultyTitle.AMATEUR.ordinal)
    val currentIndex = _currentIndex.asStateFlow()
    private fun setIndex(index: Int) {
        _currentIndex.value = index
        timerModel.let { timerModel ->
            timerModel?.setTimeRemaining(currentTime)
            timerModel?.resetTimer()
        }
    }
    fun incrementIndex() {
        var i = currentIndex.value + 1
        if (i >= difficultyRepository.itemCount) { i = 0 }

        setIndex(i)
        phaseWarnModel?.audioWarnTriggered = false
    }
    fun decrementIndex() {
        var i = currentIndex.value - 1
        if (i < 0) { i = difficultyRepository.itemCount - 1 }

        setIndex(i)
        phaseWarnModel?.audioWarnTriggered = false
    }

    /* -- */

    val currentDifficulty: DifficultyRepository.DifficultyTitle
        get() = DifficultyRepository.DifficultyTitle.entries[currentIndex.value]

    val currentName: Int
        get() = difficultyRepository.itemList[currentIndex.value].name
    fun getNameAt(index: Int): Int {
        return difficultyRepository.itemList[index].name
    }

    val currentTime: Long
        get() = difficultyRepository.itemList[currentIndex.value].time

    val currentStartSanity: Float
        get() = INSANITY_START[currentIndex.value]

    val responseTypeKnown: Boolean
        get() = currentIndex.value < DifficultyRepository.DifficultyTitle.PROFESSIONAL.ordinal

    /** Defaults if the selected index is out of range of available indexes.
     * @return the difficulty rate multiplier. 1 - default. 0-2 Depending on Map Size. */
    val currentModifier: Float
        get() {
            val diffIndex = currentIndex.value
            if (diffIndex >= 0 && diffIndex < MODIFIER.size) {
                return MODIFIER[diffIndex]
            }
            return 1f
        }
}
