package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.status

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository

class SetReviewRequestStatusUseCase(
    private val repository: ReviewTrackerRepository
) {

    suspend operator fun invoke(status: Boolean) {
        repository.saveWasRequestedStatus(status)
    }

}
