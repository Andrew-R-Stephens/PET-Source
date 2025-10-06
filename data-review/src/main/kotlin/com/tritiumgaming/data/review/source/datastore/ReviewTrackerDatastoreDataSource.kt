package com.tritiumgaming.data.review.source.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.lifecycle.liveData
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.core.domain.reviewtracker.source.ReviewTrackerDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * ReviewTrackingData class
 *
 * @author TritiumGamingStudios
 */
class ReviewTrackerDatastoreDataSource(
    context: Context,
    private val dataStore: DataStore<Preferences>
): ReviewTrackerDatastore {

    val flow: Flow<ReviewTrackerDatastore.ReviewTrackerPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            mapPreferences(preferences)
        }

    init {
        Log.d("ReviewTracking Repository", "Initializing")

        KEY_ALLOW_REQUEST_REVIEW = booleanPreferencesKey(
            context.resources.getString(R.string.reviewtracking_canRequestReview)
        )
        KEY_TIME_ACTIVE = longPreferencesKey(
            context.resources.getString(R.string.reviewtracking_appTimeAlive)
        )
        KEY_TIMES_OPENED = intPreferencesKey(
            context.resources.getString(R.string.reviewtracking_appTimesOpened)
        )
    }

    override suspend fun saveWasRequestedState(wasRequested: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_ALLOW_REQUEST_REVIEW] = wasRequested
        }
    }
    override fun getWasRequestedState(): Boolean {
        var wasRequested = false
        dataStore.data.map { preferences ->
            wasRequested = (preferences[KEY_ALLOW_REQUEST_REVIEW] == true)
        }
        return wasRequested
    }

    override suspend fun saveAppTimeAlive(time: Long) {
        dataStore.edit { preferences ->
            preferences[KEY_TIME_ACTIVE] = time
        }
    }

    override fun getAppTimeAlive(): Long {
        var timeAlive = 0L
        dataStore.data.map { preferences ->
            timeAlive = (preferences[KEY_TIME_ACTIVE] ?: 0L)
        }
        return timeAlive
    }

    override suspend fun saveAppTimesOpened(count: Int) {
        dataStore.edit { preferences ->
            preferences[KEY_TIMES_OPENED] = count
        }
    }
    override fun getAppTimesOpened(): Int {
        var timesOpened = 0
        dataStore.data.map  { preferences ->
            timesOpened = (preferences[KEY_TIMES_OPENED] ?: 0)
        }
        return timesOpened
    }

    override fun initializeDatastoreLiveData() {
        liveData { emit(fetchDatastoreInitialPreferences()) }
    }

    override suspend fun initDatastoreFlow(onUpdate: (ReviewTrackerDatastore.ReviewTrackerPreferences) -> Unit) =
        flow.collect { onUpdate(it) }

    override suspend fun fetchDatastoreInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    private fun mapPreferences(preferences: Preferences): ReviewTrackerDatastore.ReviewTrackerPreferences {
        return ReviewTrackerDatastore.ReviewTrackerPreferences(
            timeActive = preferences[KEY_TIME_ACTIVE] ?: 0L,
            timesOpened = preferences[KEY_TIMES_OPENED] ?: 0,
            allowRequestReview = preferences[KEY_ALLOW_REQUEST_REVIEW] == true,
        )
    }

    companion object PreferenceKeys {
        lateinit var KEY_ALLOW_REQUEST_REVIEW: Preferences.Key<Boolean>
        lateinit var KEY_TIME_ACTIVE: Preferences.Key<Long>
        lateinit var KEY_TIMES_OPENED: Preferences.Key<Int>
    }

}
