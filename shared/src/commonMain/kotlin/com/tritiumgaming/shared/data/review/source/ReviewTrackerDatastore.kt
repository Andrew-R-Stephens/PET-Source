package com.tritiumgaming.shared.data.review.source

import com.tritiumgaming.shared.data.datastore.DatastoreDataSource

interface ReviewTrackerDatastore: DatastoreDataSource<ReviewTrackerDatastore.ReviewTrackerPreferences> {

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