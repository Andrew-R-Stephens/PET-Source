package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.activities

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform
import java.util.concurrent.atomic.AtomicBoolean

interface ConsentManagementService {

    var consentInformation: ConsentInformation?

    // Use an atomic boolean to initialize the Google Mobile Ads SDK and load ads once.
    val isMobileAdsInitializeCalled: AtomicBoolean

    val isPrivacyOptionsRequired: Boolean
        // Show a privacy options button if required.
        get() = (consentInformation!!.privacyOptionsRequirementStatus
                == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED)

    fun createConsentInformation(activity: Activity) {
        val debugSettings = ConsentDebugSettings.Builder(activity)
            .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_OTHER)
            .addTestDeviceHashedId("00E2BE3BE3FB3298734CA8B92655E237")
            .addTestDeviceHashedId("B3C272DE5AEAB81CA9CBBCB2A928A38E")
            .build()

        // Create a ConsentRequestParameters object.
        val params = ConsentRequestParameters.Builder()
            .setConsentDebugSettings(debugSettings)
            .setTagForUnderAgeOfConsent(false)
            .build()


        consentInformation = UserMessagingPlatform.getConsentInformation(activity)
        consentInformation?.let { consentInformation ->
            consentInformation.requestConsentInfoUpdate(activity, params, {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) {
                    // Consent gathering failed.
                        loadAndShowError: FormError? ->
                    loadAndShowError?.let {
                        Log.w("ConsentManagerV2", String.format("%s: %s",
                            loadAndShowError.errorCode, loadAndShowError.message))
                    }
                }
            },
                // Consent gathering failed.
                { requestConsentError: FormError ->
                    Log.w("ConsentManagerV2", String.format("%s: %s",
                        requestConsentError.errorCode, requestConsentError.message))
                })
            // Check if you can initialize the Google Mobile Ads SDK in parallel
            // while checking for new consent information. Consent obtained in
            // the previous session can be used to request ads.
            if (consentInformation.canRequestAds()) { initializeMobileAdsSdk(activity) }
        }

    }

    fun initializeMobileAdsSdk(activity: Activity) {
        if (isMobileAdsInitializeCalled.getAndSet(true)) { return }

        // Initialize the Google Mobile Ads SDK.
        MobileAds.initialize(activity)

        // TODO: Request an ad.
        // InterstitialAd.load(...);

        val testDeviceIds = listOf("00E2BE3BE3FB3298734CA8B92655E237", "B3C272DE5AEAB81CA9CBBCB2A928A38E")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)
    }

}
