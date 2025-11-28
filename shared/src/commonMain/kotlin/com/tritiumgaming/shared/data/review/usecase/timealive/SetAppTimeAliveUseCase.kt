package com.tritiumgaming.shared.data.review.usecase.timealive

class SetAppTimeAliveUseCase(
    private val repository: com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(time: Long) {
        repository.saveAppTimeAlive(time)
    }

}