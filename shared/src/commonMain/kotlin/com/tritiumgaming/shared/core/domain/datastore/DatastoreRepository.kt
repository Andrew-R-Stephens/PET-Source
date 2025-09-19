package com.tritiumgaming.shared.core.domain.datastore
interface DatastoreRepository<T> {
    
    fun initializeDatastoreLiveData()
    suspend fun initDatastoreFlow(onUpdate: (preferences: T) -> Unit)
    
}