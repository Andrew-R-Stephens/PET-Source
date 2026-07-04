package com.tritiumgaming.data.policy.repository

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.setConsent
import com.tritiumgaming.core.common.settings.googleadsconsentmanager.GoogleMobileAdsConsentManager
import com.tritiumgaming.shared.data.policy.repository.PolicyRepository
import com.tritiumgaming.shared.data.policy.source.PolicyDatastore
import com.tritiumgaming.shared.data.policy.source.PolicyDatastore.Policy
import kotlinx.coroutines.flow.Flow

class PolicyRepositoryImpl(
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

    override fun gatherAdsConsent(activity: Any, onFinished: (error: Any?) -> Unit) {
        if (activity is Activity) {
            googleMobileAdsConsentManager.gatherConsent(activity) { error ->
                onFinished(error)
            }
        }
    }

    override suspend fun initializeMobileAds(context: Any, onFinished: () -> Unit) {
        if (context is Activity && googleMobileAdsConsentManager.canInitializeMobileAds()) {
            // Set your test devices.
            MobileAds.setRequestConfiguration(
                RequestConfiguration.Builder()
                    .setTestDeviceIds(GoogleMobileAdsConsentManager.TEST_DEVICE_HASHED_IDS)
                    .build()
            )

            // Initialize the Google Mobile Ads SDK on a background thread.
            kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                MobileAds.initialize(context) {
                    onFinished()
                }
                Log.d("InitializeMobileAds", "Mobile Ads SDK initialized.")
            }
        }
    }

    override fun applyPolicy(policy: Policy) {
        val analyticsStatus = if (policy.allowAnalytics) {
            FirebaseAnalytics.ConsentStatus.GRANTED
        } else {
            FirebaseAnalytics.ConsentStatus.DENIED
        }
        val adsStatus = if (policy.allowPersonalizedAds) {
            FirebaseAnalytics.ConsentStatus.GRANTED
        } else {
            FirebaseAnalytics.ConsentStatus.DENIED
        }

        /** Set FirebaseAnalytics consent types to
         * [Consent V2](https://developers.google.com/tag-platform/security/guides/app-consent?platform=android&consentmode=advanced#upgrade-consent-v2). */
        Firebase.analytics.setConsent {
            analyticsStorage = analyticsStatus
            adStorage = adsStatus
            adPersonalization = adsStatus
            adUserData = adsStatus
        }

        try {
            // To support Advanced Consent Mode (GCMv2), we keep collection enabled.
            // When consent is DENIED, Firebase will send cookieless pings.
            // However, to ensure a strict "deny" equivalent until affirmed,
            // we follow the user's explicit preference for analytics collection.
            Firebase.analytics.setAnalyticsCollectionEnabled(policy.allowAnalytics)
            Log.d("PrivacyControl", "Policy applied: Analytics=$analyticsStatus, Ads=$adsStatus")

            // Programmatic verification: retrieve app instance ID.
            // If consent is denied, this returns null (even if collection is enabled).
            Firebase.analytics.appInstanceId.addOnCompleteListener { task ->
                val isActive = task.isSuccessful && task.result != null
                Log.d("PrivacyControl", "Verification - Is Analytics Fully Active: $isActive -> ID: ${task.result}")
            }
        } catch (e: Exception) {
            Log.e("PrivacyControl", "Failed to apply policy", e)
        }
    }

}
