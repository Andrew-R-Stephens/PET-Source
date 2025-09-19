package com.tritiumgaming.shared.core.domain.reviewtracker.repository

import com.tritiumgaming.shared.core.domain.datastore.DatastoreRepository
import com.tritiumgaming.shared.core.domain.reviewtracker.source.ReviewTrackerDatastore

interface ReviewTrackerRepository: DatastoreRepository<ReviewTrackerDatastore.ReviewTrackerPreferences> {

    suspend fun saveWasRequestedStatus(wasRequested: Boolean)
    fun getWasRequestedStatus(): Boolean
    suspend fun loadWasRequestedStatus()

    suspend fun saveAppTimeAlive(time: Long)
    fun getAppTimeAlive(): Long
    suspend fun loadAppTimeAlive()

    suspend fun saveAppTimesOpened(count: Int)
    fun getAppTimesOpened(): Int
    suspend fun loadAppTimesOpened()

}