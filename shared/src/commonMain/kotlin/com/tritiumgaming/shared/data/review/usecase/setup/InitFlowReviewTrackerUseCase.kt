package com.tritiumgaming.shared.data.review.usecase.setup

class InitFlowReviewTrackerUseCase(
        private val repository: com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository
    ) {
    suspend operator fun invoke(onUpdate: (com.tritiumgaming.shared.data.review.source.ReviewTrackerDatastore.ReviewTrackerPreferences) -> Unit) =
        repository.initDatastoreFlow()
    }
    