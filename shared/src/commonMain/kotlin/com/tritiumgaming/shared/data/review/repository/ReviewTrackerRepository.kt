package com.tritiumgaming.shared.data.review.repository

interface ReviewTrackerRepository:
    com.tritiumgaming.shared.data.datastore.DatastoreRepository<com.tritiumgaming.shared.data.review.source.ReviewTrackerDatastore.ReviewTrackerPreferences> {

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