package com.tritiumgaming.shared.data.datastore

import kotlinx.coroutines.flow.Flow

interface DatastoreRepository<T> {
    
    fun initializeDatastoreLiveData()
    fun initDatastoreFlow(): Flow<T>
    
}