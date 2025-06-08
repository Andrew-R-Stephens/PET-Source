package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.carousels

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.sanity.SanityHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.timer.TimerHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.warning.PhaseHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DifficultyCarouselHandler(
    difficultyRepository: DifficultyRepository
) {

    private val difficulties = difficultyRepository.getDifficulties().getOrDefault(emptyList())

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
        if (i >= difficulties.size) { i = 0 }

        setIndex(sanityHandler, mapHandler, timerHandler, i)
        phaseHandler.audioWarnTriggered = false
    }
    fun decrementIndex(
        sanityHandler: SanityHandler,
        mapHandler: MapCarouselHandler,
        timerHandler: TimerHandler,
        phaseHandler: PhaseHandler) {

        var i = currentIndex.value - 1
        if (i < 0) { i = difficulties.size - 1 }

        setIndex(sanityHandler, mapHandler, timerHandler, i)
        phaseHandler.audioWarnTriggered = false
    }

    /* -- */

    val currentDifficulty: DifficultyType
        get() = DifficultyType.entries[currentIndex.value]

    private val _currentName = MutableStateFlow(
        difficulties[currentIndex.value].name
    )
    val currentName = _currentName.asStateFlow()
    fun updateCurrentName() {
        _currentName.update { difficulties[currentIndex.value].name }
    }

    fun getNameAt(index: Int): Int {
        return difficulties[index].name
    }

    val currentTime: Long
        get() = difficulties[currentIndex.value].time

    val currentStartSanity: Float
        get() = difficulties[currentIndex.value].initialSanity

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
            if (diffIndex >= 0 && diffIndex < difficulties.size) {
                return difficulties[diffIndex].modifier
            }
            return 1f
        }

}
