package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository

class SetAppTimeAliveUseCase(
    private val reviewTrackerDatastoreRepository: ReviewTrackerRepository
) {

    suspend operator fun invoke(time: Long) {
        reviewTrackerDatastoreRepository.saveAppTimeAlive(time)
    }

}