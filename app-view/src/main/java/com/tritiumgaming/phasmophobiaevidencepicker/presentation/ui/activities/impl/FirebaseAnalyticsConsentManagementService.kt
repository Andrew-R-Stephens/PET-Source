package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.impl

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.analytics.FirebaseAnalytics.ConsentStatus
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.setConsent
import com.google.firebase.ktx.Firebase


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