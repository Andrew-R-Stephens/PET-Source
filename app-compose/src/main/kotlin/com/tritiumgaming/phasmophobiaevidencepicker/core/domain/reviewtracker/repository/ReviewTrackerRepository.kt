package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore.DatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore.ReviewTrackerPreferences

interface ReviewTrackerRepository: DatastoreRepository<ReviewTrackerPreferences> {

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