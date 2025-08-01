package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timesopened

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository

class SetAppTimesOpenedUseCase(
    private val repository: ReviewTrackerRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(count: Int) {
        repository.saveAppTimesOpened(count)
    }

}