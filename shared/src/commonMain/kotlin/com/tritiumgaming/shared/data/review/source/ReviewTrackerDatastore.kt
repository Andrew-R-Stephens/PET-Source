package com.tritiumgaming.shared.data.review.source

import com.tritiumgaming.shared.data.datastore.DatastoreDataSource
import com.tritiumgaming.shared.data.review.source.ReviewTrackerDatastore.ReviewTrackerPreferences

interface ReviewTrackerDatastore: DatastoreDataSource<ReviewTrackerPreferences> {

    suspend fun saveWasRequestedState(wasRequested: Boolean)
    fun getWasRequestedState(): Boolean

    suspend fun saveAppTimeAlive(time: Long)
    fun getAppTimeAlive(): Long

    suspend fun saveAppTimesOpened(count: Int): Result<Boolean>
    fun getAppTimesOpened(): Int

    data class ReviewTrackerPreferences(
        val reviewRequested: Boolean,
        val timeActive: Long,
        val timesOpened: Int
    )

}