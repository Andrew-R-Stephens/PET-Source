package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore.ReviewTrackerPreferences

class InitFlowReviewTrackerUseCase(
        private val repository: ReviewTrackerRepository
    ) {
    suspend operator fun invoke(onUpdate: (ReviewTrackerPreferences) -> Unit) =
        repository.initDatastoreFlow(onUpdate)
    }
    