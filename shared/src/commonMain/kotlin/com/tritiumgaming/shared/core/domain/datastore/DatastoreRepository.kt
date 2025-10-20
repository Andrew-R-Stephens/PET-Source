package com.tritiumgaming.shared.core.domain.datastore

import kotlinx.coroutines.flow.Flow

interface DatastoreRepository<T> {
    
    fun initializeDatastoreLiveData()
    fun initDatastoreFlow(): Flow<T>
    
}