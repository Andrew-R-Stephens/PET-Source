package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerDatastoreRepository

class SetAppTimeAliveUseCase(
    private val reviewTrackerDatastoreRepository: ReviewTrackerDatastoreRepository
) {

    suspend operator fun invoke(time: Long) {
        reviewTrackerDatastoreRepository.saveAppTimeAlive(time)
    }

}