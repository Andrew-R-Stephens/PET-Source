package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkUtils {

    public static boolean isNetworkAvailable(Context context, boolean isMeteredNetworkAllowed) {

        if(context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                Log.d("NetworkUtils", "Network Available: Determining Network type...");
                if (!connectivityManager.isActiveNetworkMetered()) {
                    Log.d("NetworkUtils", "Connected to Wifi Network: Connection success!");
                    return true;
                } else {
                    Log.d("NetworkUtils", "Connected to Metered Network: Checking if allowed to use...");
                    if (!isMeteredNetworkAllowed) {
                        Log.d("NetworkUtils", "Metered Network Disallowed: Unable to connect.");
                        return false;
                    } else {
                        Log.d("NetworkUtils", "Metered Network Allowed: Connection success!");
                        return true;
                    }
                }
            } else {
                Log.d("NetworkUtils", "Network Unavailable: Unable to connect.");
                return false;
            }
        }
        Log.d("NetworkUtils", "Activity read as null. Connection forcefully rejected.");
        return false;
    }
}
