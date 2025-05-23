package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerDatastoreRepository

class LoadAppTimeAliveUseCase(
    private val reviewTrackerDatastoreRepository: ReviewTrackerDatastoreRepository
) {

    suspend operator fun invoke() = reviewTrackerDatastoreRepository.loadAppTimeAlive()

}
