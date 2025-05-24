package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.status

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository

class LoadReviewRequestStatusUseCase(
    private val reviewTrackerDatastoreRepository: ReviewTrackerRepository
) {

    suspend operator fun invoke() = reviewTrackerDatastoreRepository.loadWasRequestedStatus()

}
