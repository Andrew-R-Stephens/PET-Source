package com.tritiumgaming.phasmophobiaevidencepicker.core.data.reviewtracker.source.datastore

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
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore.PreferenceKeys.KEY_ALLOW_REQUEST_REVIEW
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore.PreferenceKeys.KEY_TIMES_OPENED
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore.PreferenceKeys.KEY_TIME_ACTIVE
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore.ReviewTrackerPreferences
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
    val datastore: DataStore<Preferences>
): ReviewTrackerDatastore {

    override val flow: Flow<ReviewTrackerPreferences> = datastore.data
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

    override suspend fun initFlow(onUpdate: (ReviewTrackerPreferences) -> Unit) =
        flow.collect { onUpdate(it) }

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
        datastore.edit { preferences ->
            preferences[KEY_ALLOW_REQUEST_REVIEW] = wasRequested
        }
    }
    override fun getWasRequestedState(): Boolean {
        var wasRequested = false
        datastore.data.map { preferences ->
            wasRequested = (preferences[KEY_ALLOW_REQUEST_REVIEW] == true)
        }
        return wasRequested
    }

    override suspend fun saveAppTimeAlive(time: Long) {
        datastore.edit { preferences ->
            preferences[KEY_TIME_ACTIVE] = time
        }
    }

    override fun getAppTimeAlive(): Long {
        var timeAlive = 0L
        datastore.data.map { preferences ->
            timeAlive = (preferences[KEY_TIME_ACTIVE] ?: 0L)
        }
        return timeAlive
    }

    override suspend fun saveAppTimesOpened(count: Int) {
        datastore.edit { preferences ->
            preferences[KEY_TIMES_OPENED] = count
        }
    }
    override fun getAppTimesOpened(): Int {
        var timesOpened = 0
        datastore.data.map  { preferences ->
            timesOpened = (preferences[KEY_TIMES_OPENED] ?: 0)
        }
        return timesOpened
    }

    override suspend fun fetchInitialPreferences() =
        mapPreferences(datastore.data.first().toPreferences())

    override fun mapPreferences(preferences: Preferences): ReviewTrackerPreferences {
        return ReviewTrackerPreferences(
            timeActive = preferences[KEY_TIME_ACTIVE] ?: 0L,
            timesOpened = preferences[KEY_TIMES_OPENED] ?: 0,
            allowRequestReview =  preferences[KEY_ALLOW_REQUEST_REVIEW] == true,
        )
    }

}
