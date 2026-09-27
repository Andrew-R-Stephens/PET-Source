package com.tritiumgaming.core.common.settings.googleadsconsentmanager

import android.app.Activity
import android.content.Context
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform
import java.util.concurrent.atomic.AtomicBoolean

/**
 * The Google Mobile Ads SDK provides the User Messaging Platform (Google's
 * IAB Certified consent management platform) as one solution to capture
 * consent for users in GDPR impacted countries.
 */
class GoogleMobileAdsConsentManager(
    context: Context
) {

    private val consentInformation: ConsentInformation =
        UserMessagingPlatform.getConsentInformation(context)

    /** Helper variable to determine if the app can request ads.  */
    val canRequestAds: Boolean
        get() = consentInformation.canRequestAds()

    val isPrivacyOptionsRequired: Boolean?
        get() = when (consentInformation.privacyOptionsRequirementStatus) {
            ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED -> true
            ConsentInformation.PrivacyOptionsRequirementStatus.NOT_REQUIRED -> false
            else -> null
        }

    private var isGoogleAdsConsentManagerInitialized: Boolean = false
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)

    /** Helper method to call the UMP SDK methods to request consent information and load/present a
     * consent form if necessary.  */
    fun gatherConsent(
        activity: Activity,
        onConsentGatheringCompleteListener: OnConsentGatheringCompleteListener
    ) {
        if (isGoogleAdsConsentManagerInitialized) return
        isGoogleAdsConsentManagerInitialized = true

        // For testing purposes, you can force a DebugGeography of EEA or NOT_EEA.
        val debugSettings = ConsentDebugSettings.Builder(activity)
            .apply {
                //setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                //setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_REGULATED_US_STATE)
                //setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_OTHER)
                TEST_DEVICE_HASHED_IDS.forEach { addTestDeviceHashedId(it) }
            }.build()

        val params = ConsentRequestParameters.Builder()
            .setConsentDebugSettings(debugSettings)
            .build()

        // Requesting an update to consent information should be called on every app launch.
        consentInformation.requestConsentInfoUpdate(
            activity,
            params, {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) { formError ->
                    // Consent has been gathered.
                    onConsentGatheringCompleteListener.consentGatheringComplete(formError)
                }
            }, { requestConsentError ->
                onConsentGatheringCompleteListener.consentGatheringComplete(requestConsentError)
            }
        )
    }

    /** Helper method to check if the Mobile Ads SDK should be initialized. */
    fun canInitializeMobileAds(): Boolean {
        return canRequestAds && isMobileAdsInitializeCalled
            .compareAndSet(false, true)
    }

    /** Helper method to call the UMP SDK method to present the privacy options form.  */
    fun showPrivacyOptionsForm(
        activity: Activity,
        onConsentFormDismissedListener: ConsentForm.OnConsentFormDismissedListener
    ) {
        UserMessagingPlatform.showPrivacyOptionsForm(activity, onConsentFormDismissedListener)
    }

    companion object {
        @Volatile private var instance: GoogleMobileAdsConsentManager? = null

        fun getInstance(context: Context) =
            instance
                ?: synchronized(this) {
                    instance ?: GoogleMobileAdsConsentManager(context).also { instance = it }
                }

        val TEST_DEVICE_HASHED_IDS = listOf (
            "00E2BE3BE3FB3298734CA8B92655E237",
            "B3C272DE5AEAB81CA9CBBCB2A928A38E",
            "35C63C64AD5C412021F7831FF07C5411",
            "4980A1A8D6C7BD9E599217DE73CD36EB",
            "27146712BE687C3CD0661E581B0631A4"
        )
    }

    /** Interface definition for a callback to be invoked when consent gathering is complete.  */
    fun interface OnConsentGatheringCompleteListener {
        fun consentGatheringComplete(error: FormError?)
    }

}

data class GoogleAdsConsentState(
    /** Represents current initialization states for the Google Mobile Ads SDK. */
    val isMobileAdsInitialized: Boolean = false,
    /** Indicates whether the app has completed the steps for gathering updated user consent. */
    val canRequestAds: Boolean = false,
    /** Indicates whether a privacy options form is required. */
    val isPrivacyOptionsRequired: Boolean? = null
)
