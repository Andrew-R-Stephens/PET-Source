package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerDatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowReviewTrackerUseCase(
        private val reviewTrackerDatastoreRepository: ReviewTrackerDatastoreRepository
    ) {
        suspend operator fun invoke(): Flow<ReviewTrackerDatastore.ReviewTrackerPreferences> =
            reviewTrackerDatastoreRepository.initFlow()
    }
    