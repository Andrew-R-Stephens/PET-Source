package com.tritiumgaming.shared.core.domain.reviewtracker.usecase.setup

import com.tritiumgaming.shared.core.domain.reviewtracker.repository.ReviewTrackerRepository
import com.tritiumgaming.shared.core.domain.reviewtracker.source.ReviewTrackerDatastore.ReviewTrackerPreferences

class InitFlowReviewTrackerUseCase(
        private val repository: ReviewTrackerRepository
    ) {
    suspend operator fun invoke(onUpdate: (ReviewTrackerPreferences) -> Unit) =
        repository.initDatastoreFlow()
    }
    