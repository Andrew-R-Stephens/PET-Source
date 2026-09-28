package com.tritiumgaming.data.review.usecase.timesopened

import com.tritiumgaming.data.review.repository.ReviewTrackerRepository

class GetAppTimesOpenedUseCase(
    private val repository: ReviewTrackerRepository
) {

    operator fun invoke(): Int = repository.getAppTimesOpened()

}