package com.tritiumstudios.data.policy.source.datastore

import android.content.pm.ApplicationInfo
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.tritiumstudios.data.policy.source.PolicyDatastore
import com.tritiumstudios.data.policy.source.PolicyDatastore.Policy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PolicyDatastoreDataSource(
    private val applicationInfo: ApplicationInfo,
    private val dataStore: DataStore<Preferences>,
    datastoreKeys: PolicyDatastore.Keys
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
            val bundle = applicationInfo.metaData ?: return defaultValue
            when (val value = bundle.get(key)) {
                is Boolean -> value
                is String -> {
                    if (value == "eu_consent_policy") {
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

        KEY_ALLOW_ANALYTICS = booleanPreferencesKey(datastoreKeys.allowAnalytics)
        KEY_ALLOW_PERSONALIZED_ADS = booleanPreferencesKey(datastoreKeys.allowPersonalizedAds)
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
            allowPersonalizedAds = preferences[KEY_ALLOW_PERSONALIZED_ADS]
                ?: defaultAllowPersonalizedAds,
            hasExplicitAnalyticsConsent = preferences.contains(KEY_ALLOW_ANALYTICS)
        )
    }

    companion object PreferenceKeys {
        lateinit var KEY_ALLOW_ANALYTICS: Preferences.Key<Boolean>
        lateinit var KEY_ALLOW_PERSONALIZED_ADS: Preferences.Key<Boolean>
    }

}
