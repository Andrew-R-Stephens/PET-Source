package com.tritiumgaming.shared.core.domain.reviewtracker.source

import com.tritiumgaming.shared.core.domain.datastore.DatastoreDataSource
import com.tritiumgaming.shared.core.domain.reviewtracker.source.ReviewTrackerDatastore.ReviewTrackerPreferences

interface ReviewTrackerDatastore: DatastoreDataSource<ReviewTrackerPreferences> {

    suspend fun saveWasRequestedState(wasRequested: Boolean)
    fun getWasRequestedState(): Boolean

    suspend fun saveAppTimeAlive(time: Long)
    fun getAppTimeAlive(): Long

    suspend fun saveAppTimesOpened(count: Int)
    fun getAppTimesOpened(): Int

    data class ReviewTrackerPreferences(
        val allowRequestReview: Boolean,
        val timeActive: Long,
        val timesOpened: Int
    )

}