package com.tritiumgaming.data.policy.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.setConsent
import com.tritiumgaming.core.common.settings.googleadsconsentmanager.GoogleMobileAdsConsentManager
import com.tritiumgaming.shared.data.policy.repository.PolicyRepository
import com.tritiumgaming.shared.data.policy.source.PolicyDatastore
import com.tritiumgaming.shared.data.policy.source.PolicyDatastore.Policy
import kotlinx.coroutines.flow.Flow

class PolicyRepositoryImpl(
    private val analytics: FirebaseAnalytics,
    private val dataStoreSource: PolicyDatastore,
    private val googleMobileAdsConsentManager: GoogleMobileAdsConsentManager
) : PolicyRepository {

    override fun initDatastoreFlow(): Flow<Policy> =
        dataStoreSource.initDatastoreFlow()

    override suspend fun setAllowAnalytics(allow: Boolean) {
        dataStoreSource.setAllowAnalytics(allow)
    }

    override suspend fun setAllowPersonalizedAds(allow: Boolean) {
        dataStoreSource.setAllowPersonalizedAds(allow)
    }

    override fun isPrivacyOptionsRequired(): Boolean? {
        return googleMobileAdsConsentManager.isPrivacyOptionsRequired
    }

    override fun showPrivacyOptionsForm(activity: Any, onFinished: () -> Unit) {
        if (activity is Activity) {
            googleMobileAdsConsentManager.showPrivacyOptionsForm(activity) {
                onFinished()
            }
        }
    }

    override fun applyPolicy(policy: Policy) {
        setAnalyticsEnabled(policy.allowAnalytics)
        setPersonalizedAdsEnabled(policy.allowPersonalizedAds)
    }

    private fun setAnalyticsEnabled(enabled: Boolean) {
        Log.d("PrivacyControl", "Setting analytics enabled: $enabled")
        try {
            analytics.setAnalyticsCollectionEnabled(enabled)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setPersonalizedAdsEnabled(enabled: Boolean) {
        Log.d("PrivacyControl", "Setting personalized ads enabled: $enabled")

        val consentStatus = if (enabled) {
            FirebaseAnalytics.ConsentStatus.GRANTED
        } else {
            FirebaseAnalytics.ConsentStatus.DENIED
        }

        analytics.setConsent {
            adPersonalization = consentStatus
            adUserData = consentStatus
        }
    }

}
