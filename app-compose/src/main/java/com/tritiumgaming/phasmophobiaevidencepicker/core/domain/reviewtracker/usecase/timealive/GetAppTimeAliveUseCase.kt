package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerDatastoreRepository

class GetAppTimeAliveUseCase(
    private val reviewTrackerDatastoreRepository: ReviewTrackerDatastoreRepository
) {

    operator fun invoke() = reviewTrackerDatastoreRepository.getAppTimeAlive()

}