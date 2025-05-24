package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowReviewTrackerUseCase(
        private val reviewTrackerDatastoreRepository: ReviewTrackerRepository
    ) {
        suspend operator fun invoke(): Flow<ReviewTrackerDatastore.ReviewTrackerPreferences> =
            reviewTrackerDatastoreRepository.initFlow()
    }
    