package com.tritiumgaming.shared.data.review.usecase.timesopened

class GetAppTimesOpenedUseCase(
    private val repository: com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository
) {

    operator fun invoke(): Int = repository.getAppTimesOpened()

}