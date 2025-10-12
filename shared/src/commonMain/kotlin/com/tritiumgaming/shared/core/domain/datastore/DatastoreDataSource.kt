package com.tritiumgaming.shared.core.domain.datastore

import kotlinx.coroutines.flow.Flow

interface DatastoreDataSource<T> {

    fun initializeDatastoreLiveData()

    fun initDatastoreFlow(): Flow<T>

    suspend fun fetchDatastoreInitialPreferences(): T

    companion object PreferenceKeys

}