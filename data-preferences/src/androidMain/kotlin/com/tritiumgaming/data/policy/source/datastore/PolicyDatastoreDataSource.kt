package com.tritiumgaming.data.policy.source.datastore

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.policy.source.PolicyDatastore
import com.tritiumgaming.shared.data.policy.source.PolicyDatastore.Policy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class PolicyDatastoreDataSource(
    private val context: Context,
    private val dataStore: DataStore<Preferences>
): PolicyDatastore {

    private val defaultAllowAnalytics: Boolean by lazy {
        getMetadataBoolean("google_analytics_default_allow_analytics_storage", false)
    }

    private val defaultAllowPersonalizedAds: Boolean by lazy {
        val adStorage = getMetadataBoolean("google_analytics_default_allow_ad_storage", false)
        val adPersonalization = getMetadataBoolean("google_analytics_default_allow_ad_personalization_signals", false)
        val adUserData = getMetadataBoolean("google_analytics_default_allow_ad_user_data", false)

        adStorage && adPersonalization && adUserData
    }

    private fun getMetadataBoolean(key: String, defaultValue: Boolean): Boolean {
        return try {
            val appInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
            val bundle = appInfo.metaData ?: return defaultValue
            when (val value = bundle.get(key)) {
                is Boolean -> value
                is String -> {
                    if (value == "eu_consent_policy") {
                        // For Datastore initialization, we treat "policy" as false (denied)
                        // to ensure a privacy-first default that the user can then toggle.
                        false
                    } else {
                        value.toBoolean()
                    }
                }
                else -> defaultValue
            }
        } catch (e: Exception) {
            Log.w("PolicyDatastore", "Could not read manifest meta-data for $key", e)
            defaultValue
        }
    }

    private val flow: Flow<Policy> = dataStore.data
        .catch { exception ->
            if (exception is IOException) { emit(emptyPreferences()) }
            else { throw exception }
        }
        .map { preferences ->
            mapPreferences(preferences)
        }

    init {
        Log.d("Policy Repository", "Initializing")

        KEY_ALLOW_ANALYTICS = booleanPreferencesKey(context.resources.getString(R.string.preference_allowAnalytics))
        KEY_ALLOW_PERSONALIZED_ADS = booleanPreferencesKey(context.resources.getString(R.string.preference_allowPersonalizedAds))
    }

    override suspend fun setAllowAnalytics(allow: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_ALLOW_ANALYTICS] = allow
        }
    }

    override suspend fun setAllowPersonalizedAds(allow: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_ALLOW_PERSONALIZED_ADS] = allow
        }
    }

    override fun initDatastoreFlow(): Flow<Policy> = flow

    override suspend fun fetchDatastoreInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    private fun mapPreferences(preferences: Preferences): Policy {
        return Policy(
            allowAnalytics = preferences[KEY_ALLOW_ANALYTICS] ?: defaultAllowAnalytics,
            allowPersonalizedAds = preferences[KEY_ALLOW_PERSONALIZED_ADS] ?: defaultAllowPersonalizedAds
        )
    }

    companion object PreferenceKeys {
        lateinit var KEY_ALLOW_ANALYTICS: Preferences.Key<Boolean>
        lateinit var KEY_ALLOW_PERSONALIZED_ADS: Preferences.Key<Boolean>
    }

}
