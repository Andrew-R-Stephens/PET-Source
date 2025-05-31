package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timesopened

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository

class LoadAppTimesOpenedUseCase(
    private val repository: ReviewTrackerRepository
) {

    suspend operator fun invoke() = repository.loadAppTimesOpened()

}
