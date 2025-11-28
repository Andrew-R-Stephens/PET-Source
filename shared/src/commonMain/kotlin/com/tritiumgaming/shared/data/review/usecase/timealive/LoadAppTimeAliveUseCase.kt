package com.tritiumgaming.shared.data.review.usecase.timealive

class LoadAppTimeAliveUseCase(
    private val repository: com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository
) {

    suspend operator fun invoke() = repository.loadAppTimeAlive()

}
