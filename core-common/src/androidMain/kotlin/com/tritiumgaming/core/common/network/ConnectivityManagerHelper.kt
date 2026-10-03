package com.tritiumgaming.core.common.network

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.annotation.RequiresPermission

actual class ConnectivityManagerHelper(
    private val applicationContext: Context
) {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun getActiveNetworkTransport(): Result<Int> {
        val connectivityManager = applicationContext.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return Result.failure(
            ConnectivityManagerHelperException(
                "An active Network is unavailable."
            )
        )

        Log.d(
            "ConnectivityManagerHelper",
            "Active Network Available: Determining Active Network..."
        )

        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return Result.failure(
            ConnectivityManagerHelperException(
                "Active Network capabilities are unavailable."
            )
        )

        val transportType = when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkTransportType.WIFI.ordinal
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkTransportType.CELLULAR.ordinal
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkTransportType.ETHERNET.ordinal
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> NetworkTransportType.VPN.ordinal
            else -> NetworkTransportType.UNKNOWN.ordinal
        }

        return Result.success(transportType)
    }

}

class ConnectivityManagerHelperException(message: String) : Exception(message)

enum class NetworkTransportType {
    UNKNOWN, WIFI, CELLULAR, ETHERNET, VPN
}
