package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.model

import android.app.Activity
import android.content.Context
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform
import com.google.firebase.auth.FirebaseAuth

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

    /** Interface definition for a callback to be invoked when consent gathering is complete.  */
    fun interface OnConsentGatheringCompleteListener {
        fun consentGatheringComplete(error: FormError?)
    }

    /** Helper variable to determine if the app can request ads.  */
    val canRequestAds: Boolean
        get() = consentInformation.canRequestAds()

    val isPrivacyOptionsRequired: Boolean
        get() = consentInformation.privacyOptionsRequirementStatus ==
                ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED


    /** Helper method to call the UMP SDK methods to request consent information and load/present a
     * consent form if necessary.  */
    fun gatherConsent(
        activity: Activity,
        onConsentGatheringCompleteListener: OnConsentGatheringCompleteListener
    ) {
        // For testing purposes, you can force a DebugGeography of EEA or NOT_EEA.
        val debugSettings = ConsentDebugSettings.Builder(activity)
            .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
            .addTestDeviceHashedId("00E2BE3BE3FB3298734CA8B92655E237")
            .addTestDeviceHashedId("B3C272DE5AEAB81CA9CBBCB2A928A38E")
            .addTestDeviceHashedId("35C63C64AD5C412021F7831FF07C5411")
            .addTestDeviceHashedId("4980A1A8D6C7BD9E599217DE73CD36EB")
            .build()

        val params = ConsentRequestParameters.Builder()
            .setConsentDebugSettings(debugSettings)
            .build()

        // Requesting an update to consent information should be called on every app launch.
        consentInformation.requestConsentInfoUpdate(
            activity,
            params,
            { UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) {
                formError: FormError? -> // Consent has been gathered.
                    onConsentGatheringCompleteListener.consentGatheringComplete(formError) }
            },
            { requestConsentError : FormError? ->
                onConsentGatheringCompleteListener.consentGatheringComplete(requestConsentError ) }
        )
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
    }
}