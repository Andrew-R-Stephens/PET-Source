package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.status

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository

class GetReviewRequestStatusUseCase(
    private val repository: ReviewTrackerRepository
) {

    operator fun invoke(): Boolean = repository.getWasRequestedStatus()

}