package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.impl

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics.ConsentStatus
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.setConsent


interface FirebaseAnalyticsConsentManagementService {

    fun FirebaseAnalyticsConsentManagementService.initializeFirebaseAnalyticsConsent() {
        Firebase.analytics.setConsent {
            analyticsStorage = ConsentStatus.GRANTED
            adStorage = ConsentStatus.GRANTED
            adUserData = ConsentStatus.GRANTED
            adPersonalization = ConsentStatus.GRANTED

            Log.d("Firebase", "Consent initialized.")
        }
    }

}