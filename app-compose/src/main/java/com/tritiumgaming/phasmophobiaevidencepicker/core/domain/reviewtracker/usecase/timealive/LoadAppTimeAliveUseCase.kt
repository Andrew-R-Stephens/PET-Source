package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository

class LoadAppTimeAliveUseCase(
    private val reviewTrackerDatastoreRepository: ReviewTrackerRepository
) {

    suspend operator fun invoke() = reviewTrackerDatastoreRepository.loadAppTimeAlive()

}
