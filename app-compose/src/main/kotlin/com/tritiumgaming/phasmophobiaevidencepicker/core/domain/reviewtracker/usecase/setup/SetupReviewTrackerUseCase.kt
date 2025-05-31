package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository

class SetupReviewTrackerUseCase(
    private val repository: ReviewTrackerRepository
) {
    operator fun invoke() = repository.initialSetupEvent()
}