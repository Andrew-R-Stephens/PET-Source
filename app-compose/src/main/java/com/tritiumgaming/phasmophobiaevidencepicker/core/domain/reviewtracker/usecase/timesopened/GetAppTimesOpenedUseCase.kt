package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timesopened

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerRepository

class GetAppTimesOpenedUseCase(
    private val reviewTrackerDatastoreRepository: ReviewTrackerRepository
) {

    operator fun invoke(): Int = reviewTrackerDatastoreRepository.getAppTimesOpened()

}