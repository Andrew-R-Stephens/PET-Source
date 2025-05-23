package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timesopened

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerDatastoreRepository

class LoadAppTimesOpenedUseCase(
    private val reviewTrackerDatastoreRepository: ReviewTrackerDatastoreRepository
) {

    suspend operator fun invoke() = reviewTrackerDatastoreRepository.loadAppTimesOpened()

}
