package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.views.review

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics

class ReviewLauncher {
    companion object {

        /** In-App Review */
        fun launch(activity: Activity?, analytics: FirebaseAnalytics?) {
            activity ?: return

            try {
                val manager =
                    ReviewManagerFactory.create(activity)

                val request = manager.requestReviewFlow()
                request.addOnCompleteListener { requestTask: Task<ReviewInfo?> ->
                    if (requestTask.isSuccessful) {
                        Log.e("ReviewManager", "Task Successful")
                        // We can get the ReviewInfo object
                        try {
                            manager.launchReviewFlow(activity, requestTask.result)
                                .addOnCompleteListener { Log.d("ReviewFlow", "Complete.") }
                        } catch (e: IllegalStateException) { e.printStackTrace() }

                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.setData(
                            Uri.parse(activity.resources.getString(R.string.review_storelink_website))
                        )
                        intent.setPackage("com.android.vending")

                        try {
                            activity.startActivity(intent)
                            Log.e("ReviewManager",
                                "SUCCEEDED intent navigation to the App Store.")
                        } catch (e: Exception) {
                            when(e) {
                                is IllegalStateException, is ActivityNotFoundException -> {
                                    Log.e("ReviewManager",
                                        "FAILED intent navigation to the App Store.")
                                    e.printStackTrace()

                                    val params = Bundle()
                                    params.putString("event_type", "review_navigation")
                                    params.putString("event_details", "navigation_failed")
                                    analytics?.logEvent("event_review_manager", params)
                                }
                            }
                        }
                    } else {
                        Log.e("ReviewManager", "Task Failed")
                        requestTask.exception?.printStackTrace()
                    }
                }
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }

}
