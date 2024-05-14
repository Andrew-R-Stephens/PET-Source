package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

object NetworkUtils {
    @JvmStatic
    fun isNetworkAvailable(context: Context, isMeteredNetworkAllowed: Boolean): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            Log.d(
                "NetworkUtils",
                "Network Available: Determining Network type..."
            )
            if (!connectivityManager.isActiveNetworkMetered) {
                Log.d(
                    "NetworkUtils",
                    "Connected to Wifi Network: Connection success!"
                )
                return true
            } else {
                Log.d(
                    "NetworkUtils",
                    "Connected to Metered Network: Checking if allowed to use..."
                )
                if (!isMeteredNetworkAllowed) {
                    Log.d(
                        "NetworkUtils",
                        "Metered Network Disallowed: Unable to connect."
                    )
                    return false
                } else {
                    Log.d(
                        "NetworkUtils",
                        "Metered Network Allowed: Connection success!"
                    )
                    return true
                }
            }
        }

        Log.d(
            "NetworkUtils",
            "Network Unavailable: Unable to connect."
        )
        return false
    }
}
