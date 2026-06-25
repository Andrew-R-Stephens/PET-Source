package com.tritiumgaming.data.policy.repository

import android.app.Activity
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

    /** Set FirebaseAnalytics consent types to
     * [Consent V2](https://developers.google.com/tag-platform/security/guides/app-consent?platform=android&consentmode=advanced#upgrade-consent-v2). */
    private fun setAnalyticsEnabled(enabled: Boolean) {
        val consentStatus = if (enabled) {
            FirebaseAnalytics.ConsentStatus.GRANTED
        } else {
            FirebaseAnalytics.ConsentStatus.DENIED
        }

        if(consentStatus == FirebaseAnalytics.ConsentStatus.DENIED) {
            analytics.resetAnalyticsData()
            Log.d("PrivacyControl", "Analytics manually reset data")
        }

        analytics.setConsent {
            analyticsStorage = consentStatus
        }

        try {
            analytics.setAnalyticsCollectionEnabled(enabled)
            Log.d("PrivacyControl", "Analytics collection permission set to: $enabled")

            // Programmatic verification: retrieve app instance ID.
            // If disabled/denied, this returns null.
            analytics.appInstanceId.addOnCompleteListener { task ->
                val isActive = task.isSuccessful && task.result != null
                Log.d("PrivacyControl", "Verification - Is Analytics Active (AppInstanceId exists): $isActive")
            }
        } catch (e: Exception) {
            Log.e("PrivacyControl", "Failed to set analytics collection enabled", e)
        }
    }

    private fun setPersonalizedAdsEnabled(enabled: Boolean) {
        val consentStatus = if (enabled) {
            FirebaseAnalytics.ConsentStatus.GRANTED
        } else {
            FirebaseAnalytics.ConsentStatus.DENIED
        }

        analytics.setConsent {
            adStorage = consentStatus
            adPersonalization = consentStatus
            adUserData = consentStatus
        }
        Log.d("PrivacyControl", "Personalized ads consent state set to: $consentStatus")
    }

}
