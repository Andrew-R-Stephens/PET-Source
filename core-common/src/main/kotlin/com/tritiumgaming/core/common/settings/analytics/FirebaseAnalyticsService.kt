package com.tritiumgaming.core.common.settings.analytics

import android.app.Activity
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics

interface FirebaseAnalyticsService {

    var firebaseAnalytics: FirebaseAnalytics?

    /** Set FirebaseAnalytics consent types to
     * [Consent V2](https://developers.google.com/tag-platform/security/guides/app-consent?platform=android&consentmode=advanced#upgrade-consent-v2). */
    fun initFirebaseAnalytics(activity: Activity) {
        //Obtain FirebaseAnalytics instance
        try { firebaseAnalytics = FirebaseAnalytics.getInstance(activity)
            Log.d("Firebase", "Obtained instance.")
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

}