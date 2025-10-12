package com.tritiumgaming.shared.core.domain.datastore

import kotlinx.coroutines.flow.Flow

interface DatastoreRepository<T> {
    
    fun initializeDatastoreLiveData()
    fun initDatastoreFlow(onUpdate: (preferences: T) -> Unit): Flow<T>
    
}