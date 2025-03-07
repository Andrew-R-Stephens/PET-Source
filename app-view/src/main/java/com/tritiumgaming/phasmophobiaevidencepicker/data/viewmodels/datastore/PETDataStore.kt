package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.first

open class PETDataStore(private val dataStore: DataStore<Preferences>) {

    suspend fun save(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        save(dataStoreKey, value)
    }

    suspend fun save(key: String, value: Set<String>) {
        val dataStoreKey = stringSetPreferencesKey(key)
        save(dataStoreKey, value)
    }

    suspend fun save(key: String, value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(key)
        save(dataStoreKey, value)
    }

    suspend fun save(key: String, value: Long) {
        val dataStoreKey = longPreferencesKey(key)
        save(dataStoreKey, value)
    }

    suspend fun save(key: String, value: Float) {
        val dataStoreKey = floatPreferencesKey(key)
        save(dataStoreKey, value)
    }

    suspend fun save(key: String, value: Int) {
        val dataStoreKey = intPreferencesKey(key)
        save(dataStoreKey, value)
    }

    suspend fun save(key: String, value: Double) {
        val dataStoreKey = doublePreferencesKey(key)
        save(dataStoreKey, value)
    }

    private suspend fun <T> save(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences: MutablePreferences ->
            preferences[key] = value
        }
    }

    suspend fun readString(key: String, value: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        return read(dataStoreKey)
    }

    suspend fun readStringSet(key: String, value: Set<String>): Set<String>? {
        val dataStoreKey = stringSetPreferencesKey(key)
        return read(dataStoreKey)
    }

    suspend fun readBoolean(key: String, value: Boolean): Boolean? {
        val dataStoreKey = booleanPreferencesKey(key)
        return read(dataStoreKey)
    }

    suspend fun readLong(key: String): Long? {
        val dataStoreKey = longPreferencesKey(key)
        return read(dataStoreKey)
    }

    suspend fun readFloat(key: String, value: Float): Float? {
        val dataStoreKey = floatPreferencesKey(key)
        return read(dataStoreKey)
    }

    suspend fun readInt(key: String, value: Int): Int? {
        val dataStoreKey = intPreferencesKey(key)
        return read(dataStoreKey)
    }

    suspend fun readDouble(key: String, value: Double): Double? {
        val dataStoreKey = doublePreferencesKey(key)
        return read(dataStoreKey)
    }

    private suspend fun <T> read(dataStoreKey: Preferences.Key<T>): T? {
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }


}
