package com.tritiumgaming.phasmophobiaevidencepicker.core.data.reviewtracker.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerDatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore
import kotlinx.coroutines.flow.Flow

class ReviewTrackerDatastoreRepositoryImpl(
    private val dataStoreSource: ReviewTrackerDatastore,
): ReviewTrackerDatastoreRepository {

    override fun initialSetupEvent() {
        dataStoreSource.initialSetupEvent()
    }

    override suspend fun initFlow(): Flow<ReviewTrackerDatastore.ReviewTrackerPreferences> =
        dataStoreSource.flow

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
