package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log

class ConnectivityManagerHelper(private val applicationContext: Context) {

    fun getActiveNetworkTransport(): Result<Int> {
        val connectivityManager = applicationContext.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        if(network == null) {
            return Result.failure(ConnectivityManagerHelperException(
                "An active Network is unavailable."))
        }

        Log.d(
            "ConnectivityManagerHelper",
            "Active Network Available: Determining Active Network..."
        )
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(network)
        if(activeNetwork == null) {
            return Result.failure(ConnectivityManagerHelperException(
                "Active Network Available: Determining Active Network..."))
        }
        Log.d(
            "ConnectivityManagerHelper",
            "Active Network Available: Determining Active Network..."
        )

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                Result.success(NetworkCapabilities.TRANSPORT_WIFI)
            }
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                Result.success(NetworkCapabilities.TRANSPORT_CELLULAR)
            }
            // for other device how are able to connect with Ethernet
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                Result.success(NetworkCapabilities.TRANSPORT_ETHERNET)
            }
            // for check internet over Bluetooth
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> {
                Result.success(NetworkCapabilities.TRANSPORT_BLUETOOTH)
            }
            else -> {
                return Result.failure(ConnectivityManagerHelperException(
                    "Network is unavailable. There are no Active Network Transports."))
            }
        }
    }

    class ConnectivityManagerHelperException(message: String) : RuntimeException(message)
}
