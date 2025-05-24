package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.status

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository

class SetReviewRequestStatusUseCase(
    private val reviewTrackerDatastoreRepository: ReviewTrackerRepository
) {

    suspend operator fun invoke(status: Boolean) {
        reviewTrackerDatastoreRepository.saveWasRequestedStatus(status)
    }

}
