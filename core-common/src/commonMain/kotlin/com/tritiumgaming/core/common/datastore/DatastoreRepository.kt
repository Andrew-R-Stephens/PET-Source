package com.tritiumgaming.core.common.datastore

import kotlinx.coroutines.flow.Flow

interface DatastoreRepository<T> {
    
    fun initDatastoreFlow(): Flow<T>
    
}