package com.tritiumgaming.phasmophobiaevidencepicker.core.data.datastore
interface DatastoreRepository<T> {
    
    fun initialSetupEvent()
    suspend fun initFlow(onUpdate: (preferences: T) -> Unit)
    
}