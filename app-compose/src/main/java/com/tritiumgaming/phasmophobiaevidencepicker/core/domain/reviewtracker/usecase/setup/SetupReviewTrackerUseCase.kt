package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerDatastoreRepository

class SetupReviewTrackerUseCase(
    private val reviewTrackerDatastoreRepository: ReviewTrackerDatastoreRepository
) {
    operator fun invoke() = reviewTrackerDatastoreRepository.initialSetupEvent()
}