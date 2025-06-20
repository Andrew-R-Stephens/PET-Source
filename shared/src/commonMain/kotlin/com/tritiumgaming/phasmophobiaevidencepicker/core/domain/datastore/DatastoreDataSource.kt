package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.Flow

interface DatastoreDataSource<T> {

    suspend fun initFlow(onUpdate: (T) -> Unit)

    suspend fun fetchInitialPreferences(): T

    companion object PreferenceKeys

}