package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository

class GetAppTimeAliveUseCase(
    private val repository: ReviewTrackerRepository
) {

    operator fun invoke() = repository.getAppTimeAlive()

}