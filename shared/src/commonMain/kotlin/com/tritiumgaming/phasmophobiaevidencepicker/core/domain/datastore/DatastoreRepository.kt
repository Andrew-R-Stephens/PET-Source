package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore
interface DatastoreRepository<T> {
    
    fun initialSetupEvent()
    suspend fun initFlow(onUpdate: (preferences: T) -> Unit)
    
}