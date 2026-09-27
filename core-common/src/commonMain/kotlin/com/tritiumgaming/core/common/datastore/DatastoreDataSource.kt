package com.tritiumgaming.core.common.datastore

import kotlinx.coroutines.flow.Flow

interface DatastoreDataSource<T> {

    fun initDatastoreFlow(): Flow<T>

    suspend fun fetchDatastoreInitialPreferences(): T

    companion object PreferenceKeys

}