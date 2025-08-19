package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore
interface DatastoreRepository<T> {
    
    fun initializeDatastoreLiveData()
    suspend fun initDatastoreFlow(onUpdate: (preferences: T) -> Unit)
    
}