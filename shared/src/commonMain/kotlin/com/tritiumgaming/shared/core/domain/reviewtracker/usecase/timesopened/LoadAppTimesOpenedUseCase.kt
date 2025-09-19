package com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timesopened

import com.tritiumgaming.shared.core.domain.reviewtracker.repository.ReviewTrackerRepository

class LoadAppTimesOpenedUseCase(
    private val repository: ReviewTrackerRepository
) {

    suspend operator fun invoke() = repository.loadAppTimesOpened()

}
