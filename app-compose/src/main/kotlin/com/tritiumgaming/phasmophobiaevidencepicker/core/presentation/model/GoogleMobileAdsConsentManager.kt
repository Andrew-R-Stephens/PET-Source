package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.model

import android.app.Activity
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform

/**
 * The Google Mobile Ads SDK provides the User Messaging Platform (Google's
 * IAB Certified consent management platform) as one solution to capture
 * consent for users in GDPR impacted countries.
 */
class GoogleMobileAdsConsentManager(private val activity: Activity) {
    private val consentInformation: ConsentInformation =
        UserMessagingPlatform.getConsentInformation(activity)

    /** Interface definition for a callback to be invoked when consent gathering is complete.  */
    interface OnConsentGatheringCompleteListener {
        fun consentGatheringComplete(error: FormError?)
    }

    /** Helper variable to determine if the app can request ads.  */
    fun canRequestAds(): Boolean {
        return consentInformation.canRequestAds()
    }

    val isPrivacyOptionsRequired: Boolean
        /** Helper variable to determine if the privacy options form is required.  */
        get() = (consentInformation.privacyOptionsRequirementStatus
                == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED)

    /** Helper method to call the UMP SDK methods to request consent information and load/present a
     * consent form if necessary.  */
    fun gatherConsent(
        onConsentGatheringCompleteListener: OnConsentGatheringCompleteListener
    ) {
        // For testing purposes, you can force a DebugGeography of EEA or NOT_EEA.
        val debugSettings = ConsentDebugSettings.Builder(activity)
            .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA) // Check your logcat output for the hashed device ID e.g.
            // "Use new ConsentDebugSettings.Builder().addTestDeviceHashedId("ABCDEF012345")" to use
            // the debug functionality.
            //.addTestDeviceHashedId("9E93747E0D90133B5298FD010482BD8F")
            .build()

        val params = ConsentRequestParameters.Builder()
            .setConsentDebugSettings(debugSettings)
            .build()

        // Requesting an update to consent information should be called on every app launch.
        consentInformation.requestConsentInfoUpdate(
            activity,
            params,
            { // Consent has been gathered.
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                    activity
                ) { error: FormError? ->
                    onConsentGatheringCompleteListener.consentGatheringComplete(
                        error
                    )
                }
            },
            { error: FormError? -> onConsentGatheringCompleteListener.consentGatheringComplete(error) }
        )
    }

    /** Helper method to call the UMP SDK method to present the privacy options form.  */
    fun showPrivacyOptionsForm(
        activity: Activity,
        onConsentFormDismissedListener: ConsentForm.OnConsentFormDismissedListener
    ) {
        UserMessagingPlatform.showPrivacyOptionsForm(activity, onConsentFormDismissedListener)
    }
}