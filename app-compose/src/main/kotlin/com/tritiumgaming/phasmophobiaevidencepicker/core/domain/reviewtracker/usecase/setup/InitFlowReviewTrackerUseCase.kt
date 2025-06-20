package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.setup

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore.LanguagePreferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore.ReviewTrackerPreferences
import kotlinx.coroutines.flow.Flow

class InitFlowReviewTrackerUseCase(
        private val repository: ReviewTrackerRepository
    ) {
    suspend operator fun invoke(onUpdate: (ReviewTrackerPreferences) -> Unit) =
        repository.initFlow(onUpdate)
    }
    