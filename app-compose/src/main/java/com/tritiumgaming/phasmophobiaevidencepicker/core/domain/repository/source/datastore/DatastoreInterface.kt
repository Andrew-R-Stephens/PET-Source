package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.source.datastore

import androidx.datastore.preferences.core.Preferences

interface DatastoreInterface<T> {

    fun initialSetupEvent()
    suspend fun fetchInitialPreferences(): T
    fun mapPreferences(preferences: Preferences): T

    companion object PreferenceKeys

}