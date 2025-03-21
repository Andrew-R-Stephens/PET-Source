package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import com.tritiumgaming.phasmophobiaevidencepicker.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map


class ReviewTrackingRepository(
    private val dataStore: DataStore<Preferences>,
    context: Context
) {

    companion object {
        lateinit var KEY_ALLOW_REQUEST_REVIEW: Preferences.Key<Boolean>
        lateinit var KEY_TIME_ACTIVE: Preferences.Key<Long>
        lateinit var KEY_TIMES_OPENED: Preferences.Key<Int>

        private const val MAX_TIMES_OPENED_TARGET: Int = 5
    }

    init {
        Log.d("ReviewTracking Repository", "Initializing")

        KEY_ALLOW_REQUEST_REVIEW = booleanPreferencesKey(
            context.resources.getString(R.string.reviewtracking_canRequestReview))
        KEY_TIME_ACTIVE = longPreferencesKey(
            context.resources.getString(R.string.reviewtracking_appTimeAlive))
        KEY_TIMES_OPENED = intPreferencesKey(
            context.resources.getString(R.string.reviewtracking_appTimesOpened))
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
            setWasRequested(preferences[KEY_ALLOW_REQUEST_REVIEW] ?: false)
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

}
