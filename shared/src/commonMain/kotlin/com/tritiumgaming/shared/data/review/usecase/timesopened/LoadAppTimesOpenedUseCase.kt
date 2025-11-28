package com.tritiumgaming.shared.data.review.usecase.timesopened

class LoadAppTimesOpenedUseCase(
    private val repository: com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository
) {

    suspend operator fun invoke() = repository.loadAppTimesOpened()

}
