package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository

class LoadAppTimeAliveUseCase(
    private val repository: ReviewTrackerRepository
) {

    suspend operator fun invoke() = repository.loadAppTimeAlive()

}
