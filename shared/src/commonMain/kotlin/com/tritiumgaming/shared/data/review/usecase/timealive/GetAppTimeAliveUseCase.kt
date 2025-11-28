package com.tritiumgaming.shared.data.review.usecase.timealive

class GetAppTimeAliveUseCase(
    private val repository: com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository
) {

    operator fun invoke() = repository.getAppTimeAlive()

}