package com.tritiumgaming.phasmophobiaevidencepicker.app

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform
import com.tritiumgaming.phasmophobiaevidencepicker.app.container.AppContainer
import com.tritiumgaming.phasmophobiaevidencepicker.app.container.dataStore
import java.util.Arrays
import java.util.Locale

class PETApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()

        container = AppContainer(applicationContext, dataStore)

    }

}

