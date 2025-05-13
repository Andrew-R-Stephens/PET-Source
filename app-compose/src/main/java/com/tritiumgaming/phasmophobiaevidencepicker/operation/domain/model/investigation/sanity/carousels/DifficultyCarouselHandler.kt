package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.carousels

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.DifficultyRepository.DifficultyType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.sanity.SanityHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.timer.TimerHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.warning.PhaseHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DifficultyCarouselHandler(
    private val difficultyRepository: DifficultyRepository
) {
    /* Index */
    private val _currentIndex: MutableStateFlow<Int> =
        MutableStateFlow(DifficultyType.AMATEUR.ordinal)
    val currentIndex = _currentIndex.asStateFlow()
    private fun setIndex(
        sanityHandler: SanityHandler,
        mapHandler: MapCarouselHandler,
        timerHandler: TimerHandler,
        index: Int
    ) {
        _currentIndex.update { index }
        onUpdateIndex(timerHandler, sanityHandler, index, mapHandler)
    }

    private fun onUpdateIndex(
        timerHandler: TimerHandler,
        sanityHandler: SanityHandler,
        index: Int,
        mapHandler: MapCarouselHandler
    ) {
        updateCurrentName()

        timerHandler.apply {
            setTimeRemaining(currentTime)
            resetTimer(sanityHandler)
        }

        sanityHandler.updateCurrentMaxSanity(DifficultyType.entries[index])
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

    val currentDifficulty: DifficultyType
        get() = DifficultyType.entries[currentIndex.value]

    private val _currentName = MutableStateFlow(
        difficultyRepository.difficulties[currentIndex.value].name
    )
    val currentName = _currentName.asStateFlow()
    fun updateCurrentName() {
        _currentName.update { difficultyRepository.difficulties[currentIndex.value].name }
    }

    fun getNameAt(index: Int): Int {
        return difficultyRepository.difficulties[index].name
    }

    val currentTime: Long
        get() = difficultyRepository.difficulties[currentIndex.value].time

    val currentStartSanity: Float
        get() = difficultyRepository.difficulties[currentIndex.value].initialSanity

    private val _responseTypeUi = MutableStateFlow(false)
    val responseTypeUi = _responseTypeUi.asStateFlow()
    fun updateResponseTypeUi() {
        _responseTypeUi.update { currentIndex.value < DifficultyType.PROFESSIONAL.ordinal }
    }

    /** Defaults if the selected index is out of range of available indexes.
     * @return the difficulty rate multiplier. 1 - default. 0-2 Depending on Map Size. */
    val currentModifier: Float
        get() {
            val diffIndex = currentIndex.value
            if (diffIndex >= 0 && diffIndex < difficultyRepository.difficulties.size) {
                return difficultyRepository.difficulties[diffIndex].modifier
            }
            return 1f
        }

}
