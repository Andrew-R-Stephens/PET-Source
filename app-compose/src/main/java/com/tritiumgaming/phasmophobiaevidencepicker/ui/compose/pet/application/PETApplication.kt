package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.pet.application

import android.app.Application
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.pet.application.data.AppContainer
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.pet.application.data.dataStore


class PETApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()

        container = AppContainer(applicationContext, dataStore)
    }

}

