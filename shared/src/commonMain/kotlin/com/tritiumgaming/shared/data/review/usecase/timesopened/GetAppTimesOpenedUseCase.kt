package com.tritiumgaming.shared.data.review.usecase.timesopened

import com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository

class GetAppTimesOpenedUseCase(
    private val repository: ReviewTrackerRepository
) {

    operator fun invoke(): Int = repository.getAppTimesOpened()

}