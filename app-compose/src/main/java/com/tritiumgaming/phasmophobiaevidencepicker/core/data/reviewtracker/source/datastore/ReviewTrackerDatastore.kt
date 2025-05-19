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
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore.PreferenceKeys.KEY_ALLOW_REQUEST_REVIEW
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore.PreferenceKeys.KEY_TIMES_OPENED
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore.PreferenceKeys.KEY_TIME_ACTIVE
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore.ReviewTrackerPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * ReviewTrackingData class
 *
 * @author TritiumGamingStudios
 */
class ReviewTrackerDatastore(
    context: Context,
    private val dataStore: DataStore<Preferences>
): ReviewTrackerDatastore {

    override val flow: Flow<ReviewTrackerPreferences> = dataStore.data
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

    // Checks if the Review was already requested
    private val _wasRequested = MutableStateFlow(false)
    var wasRequested = _wasRequested.asStateFlow()
    suspend fun setWasRequested(wasRequested: Boolean) {
        _wasRequested.value = wasRequested
        saveWasRequested()
    }
    fun loadWasRequested() {
        dataStore.data.map { preferences ->
            setWasRequested(preferences[KEY_ALLOW_REQUEST_REVIEW] == true)
        }
    }
    suspend fun saveWasRequested() {
        dataStore.edit { preferences ->
            preferences[KEY_ALLOW_REQUEST_REVIEW] = wasRequested.value
        }
    }

    // Current time app was alive / target time to trigger review request
    private val _timeActive = MutableStateFlow(0L)
    var timeActive = _timeActive.asStateFlow()
    private suspend fun setTimeActive(time: Long) {
        _timeActive.value = time
        saveTimeActive()

    }
    fun loadTimeActive() {
        dataStore.data.map { preferences ->
            setTimeActive(preferences[KEY_TIME_ACTIVE] ?: 0L)
        }
    }
    suspend fun saveTimeActive() {
        dataStore.edit { preferences ->
            preferences[KEY_TIME_ACTIVE] = timeActive.value
        }
    }

    // Count of times app was opened / target count to trigger review request
    private val _timesOpened = MutableStateFlow(0)
    var timesOpened = _timesOpened.asStateFlow()
    private fun setTimesOpened(count: Int) {
        _timesOpened.value = count
    }
    suspend fun incrementTimesOpened() {
        _timesOpened.value ++
        saveTimesOpened()
    }
    fun loadTimesOpened() {
        dataStore.data.map  { preferences ->
            setTimesOpened(preferences[KEY_TIMES_OPENED] ?: 0)
        }
    }
    suspend fun saveTimesOpened() {
        dataStore.edit { preferences ->
            preferences[KEY_TIMES_OPENED] = timesOpened.value
        }
    }

    fun canRequestReview(): Boolean {
        return (!wasRequested.value) && (timesOpened.value >= MAX_TIMES_OPENED_TARGET)
    }

    fun canShowReviewButton(): Boolean {
        return (wasRequested.value) && (timesOpened.value >= MAX_TIMES_OPENED_TARGET)
    }

    override fun toString(): String {
        return "Time Active: " + timeActive + "; Times Opened: " + timesOpened +
                "; Can Request Review: " + canRequestReview()
    }

    override suspend fun fetchInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    override fun mapPreferences(preferences: Preferences): ReviewTrackerPreferences {
        return ReviewTrackerPreferences(
            timeActive = preferences[KEY_TIME_ACTIVE] ?: 0L,
            timesOpened = preferences[KEY_TIMES_OPENED] ?: 0,
            allowRequestReview =  preferences[KEY_ALLOW_REQUEST_REVIEW] == true,
        )
    }

    companion object {
        private const val MAX_TIMES_OPENED_TARGET: Int = 5
    }
}
