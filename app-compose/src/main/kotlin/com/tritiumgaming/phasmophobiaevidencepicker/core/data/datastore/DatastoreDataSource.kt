package com.tritiumgaming.phasmophobiaevidencepicker.core.data.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.Flow

interface DatastoreDataSource<T> {

    val flow: Flow<T>

    suspend fun initFlow(onUpdate: (T) -> Unit)

    fun initialSetupEvent() {
        liveData {
            emit(fetchInitialPreferences())
        }
    }
    suspend fun fetchInitialPreferences(): T
    fun mapPreferences(preferences: Preferences): T

    companion object PreferenceKeys

}