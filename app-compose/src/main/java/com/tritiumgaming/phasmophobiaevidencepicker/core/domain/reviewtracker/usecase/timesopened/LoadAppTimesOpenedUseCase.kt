package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timesopened

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository

class LoadAppTimesOpenedUseCase(
    private val reviewTrackerDatastoreRepository: ReviewTrackerRepository
) {

    suspend operator fun invoke() = reviewTrackerDatastoreRepository.loadAppTimesOpened()

}
