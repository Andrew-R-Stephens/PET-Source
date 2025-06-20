package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore

interface DatastoreDataSource<T> {

    suspend fun initFlow(onUpdate: (T) -> Unit)

    suspend fun fetchInitialPreferences(): T

    companion object PreferenceKeys

}