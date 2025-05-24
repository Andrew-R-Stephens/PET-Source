package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository

class GetAppTimeAliveUseCase(
    private val reviewTrackerDatastoreRepository: ReviewTrackerRepository
) {

    operator fun invoke() = reviewTrackerDatastoreRepository.getAppTimeAlive()

}