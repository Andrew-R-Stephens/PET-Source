package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timesopened

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerDatastoreRepository

class GetAppTimesOpenedUseCase(
    private val reviewTrackerDatastoreRepository: ReviewTrackerDatastoreRepository
) {

    operator fun invoke(): Int = reviewTrackerDatastoreRepository.getAppTimesOpened()

}