package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore
import kotlinx.coroutines.flow.Flow

interface ReviewTrackerDatastoreRepository {

    fun initialSetupEvent()
    suspend fun initFlow(): Flow<ReviewTrackerDatastore.ReviewTrackerPreferences>

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