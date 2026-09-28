package com.tritiumgaming.data.review.usecase.timesopened

import com.tritiumgaming.data.review.repository.ReviewTrackerRepository

class IncrementAppTimesOpenedByUseCase(
    private val repository: ReviewTrackerRepository
) {

    suspend operator fun invoke(count: Int, incrementBy: Int): Result<Boolean> {
        val canIncrement = repository.canIncrementAppTimesOpened().getOrThrow()

        return if(canIncrement) {
            repository.saveAppTimesOpened(count + incrementBy)
            Result.success(true)
        } else Result.failure(Exception("Could not increment app times opened"))

    }

}