package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.carousels

import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.DifficultyRepository.DifficultyConstraints.INSANITY_START
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.DifficultyRepository.DifficultyConstraints.MODIFIER
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.DifficultyRepository.DifficultyTitle
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.sanity.SanityHandler
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.timer.TimerHandler
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.warning.PhaseHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DifficultyCarouselHandler(
    private val difficultyRepository: DifficultyRepository
) {
    /* Index */
    private val _currentIndex: MutableStateFlow<Int> =
        MutableStateFlow(DifficultyTitle.AMATEUR.ordinal)
    val currentIndex = _currentIndex.asStateFlow()
    private fun setIndex(
        sanityHandler: SanityHandler,
        mapHandler: MapCarouselHandler,
        timerHandler: TimerHandler,
        index: Int
    ) {
        _currentIndex.value = index
        timerHandler.apply {
            setTimeRemaining(currentTime)
            resetTimer(sanityHandler)
        }

        sanityHandler.updateCurrentMaxSanity(DifficultyTitle.entries[index])
        sanityHandler.updateDrainModifier(this, mapHandler, timerHandler)

        updateResponseTypeUi()
    }
    fun incrementIndex(
        sanityHandler: SanityHandler,
        mapHandler: MapCarouselHandler,
        timerHandler: TimerHandler,
        phaseHandler: PhaseHandler) {

        var i = currentIndex.value + 1
        if (i >= difficultyRepository.itemCount) { i = 0 }

        setIndex(sanityHandler, mapHandler, timerHandler, i)
        phaseHandler.audioWarnTriggered = false
    }
    fun decrementIndex(
        sanityHandler: SanityHandler,
        mapHandler: MapCarouselHandler,
        timerHandler: TimerHandler,
        phaseHandler: PhaseHandler) {

        var i = currentIndex.value - 1
        if (i < 0) { i = difficultyRepository.itemCount - 1 }

        setIndex(sanityHandler, mapHandler, timerHandler, i)
        phaseHandler.audioWarnTriggered = false
    }

    /* -- */

    val currentDifficulty: DifficultyTitle
        get() = DifficultyTitle.entries[currentIndex.value]

    val currentName: Int
        get() = difficultyRepository.itemList[currentIndex.value].name
    fun getNameAt(index: Int): Int {
        return difficultyRepository.itemList[index].name
    }

    val currentTime: Long
        get() = difficultyRepository.itemList[currentIndex.value].time

    val currentStartSanity: Float
        get() = INSANITY_START[currentIndex.value]

    private val _responseTypeUi = MutableStateFlow(false)
    val responseTypeUi = _responseTypeUi.asStateFlow()
    fun updateResponseTypeUi() {
        _responseTypeUi.update { currentIndex.value < DifficultyTitle.PROFESSIONAL.ordinal }
    }

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
