package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.status

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerDatastoreRepository

class SetReviewRequestStatusUseCase(
    private val reviewTrackerDatastoreRepository: ReviewTrackerDatastoreRepository
) {

    suspend operator fun invoke(status: Boolean) {
        reviewTrackerDatastoreRepository.saveWasRequestedStatus(status)
    }

}
