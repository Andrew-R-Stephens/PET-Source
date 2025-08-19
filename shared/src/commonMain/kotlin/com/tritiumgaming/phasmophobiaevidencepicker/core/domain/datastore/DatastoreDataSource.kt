package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore

interface DatastoreDataSource<T> {

    fun initializeDatastoreLiveData()

    suspend fun initDatastoreFlow(onUpdate: (T) -> Unit)

    suspend fun fetchDatastoreInitialPreferences(): T

    companion object PreferenceKeys

}