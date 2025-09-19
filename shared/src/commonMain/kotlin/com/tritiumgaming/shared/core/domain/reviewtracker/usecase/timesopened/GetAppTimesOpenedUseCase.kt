package com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timesopened

import com.tritiumgaming.shared.core.domain.reviewtracker.repository.ReviewTrackerRepository

class GetAppTimesOpenedUseCase(
    private val repository: ReviewTrackerRepository
) {

    operator fun invoke(): Int = repository.getAppTimesOpened()

}