package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.status

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerDatastoreRepository

class GetReviewRequestStatusUseCase(
    private val reviewTrackerDatastoreRepository: ReviewTrackerDatastoreRepository
) {

    operator fun invoke(): Boolean = reviewTrackerDatastoreRepository.getWasRequestedStatus()

}