package com.tritiumgaming.data.review.repository

import com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository
import com.tritiumgaming.shared.data.review.source.ReviewTrackerDatastore

class ReviewTrackerRepositoryImpl(
    private val dataStoreSource: ReviewTrackerDatastore,
): ReviewTrackerRepository {

    override fun initializeDatastoreLiveData() = dataStoreSource.initializeDatastoreLiveData()

    override fun initDatastoreFlow() = dataStoreSource.initDatastoreFlow()

    override suspend fun saveWasRequestedStatus(wasRequested: Boolean) =
        dataStoreSource.saveWasRequestedState(wasRequested)
    override fun getWasRequestedStatus(): Boolean = dataStoreSource.getWasRequestedState()
    override suspend fun loadWasRequestedStatus() =
        dataStoreSource.saveWasRequestedState(dataStoreSource.getWasRequestedState())

    override suspend fun saveAppTimeAlive(time: Long) = dataStoreSource.saveAppTimeAlive(time)
    override fun getAppTimeAlive(): Long = dataStoreSource.getAppTimeAlive()
    override suspend fun loadAppTimeAlive() =
        dataStoreSource.saveAppTimeAlive(dataStoreSource.getAppTimeAlive())

    override suspend fun saveAppTimesOpened(count: Int) = dataStoreSource.saveAppTimesOpened(count)
    override fun getAppTimesOpened(): Int = dataStoreSource.getAppTimesOpened()
    override suspend fun loadAppTimesOpened() =
        dataStoreSource.saveAppTimesOpened(dataStoreSource.getAppTimesOpened())


}
