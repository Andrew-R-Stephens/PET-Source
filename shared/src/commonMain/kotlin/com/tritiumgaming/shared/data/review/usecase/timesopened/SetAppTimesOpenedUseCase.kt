package com.tritiumgaming.shared.data.review.usecase.timesopened

class SetAppTimesOpenedUseCase(
    private val repository: com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(count: Int) {
        repository.saveAppTimesOpened(count)
    }

}