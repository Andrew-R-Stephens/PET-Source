package com.tritiumgaming.shared.data.review.repository

import com.tritiumgaming.shared.data.datastore.DatastoreRepository
import com.tritiumgaming.shared.data.review.source.ReviewTrackerDatastore

interface ReviewTrackerRepository:
    DatastoreRepository<ReviewTrackerDatastore.ReviewTrackerPreferences> {

    var appInitializationState: Int

    suspend fun saveWasRequestedStatus(wasRequested: Boolean)
    fun getWasRequestedStatus(): Boolean

    suspend fun saveAppTimeAlive(time: Long)
    fun getAppTimeAlive(): Long

    fun canIncrementAppTimesOpened(): Result<Boolean>
    suspend fun saveAppTimesOpened(count: Int): Result<Boolean>
    fun getAppTimesOpened(): Int

    companion object {
        const val NOT_INITIALIZED = 0
        const val INITIALIZED = 0
    }

}