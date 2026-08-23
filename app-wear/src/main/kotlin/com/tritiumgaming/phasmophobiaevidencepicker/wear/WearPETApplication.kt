package com.tritiumgaming.phasmophobiaevidencepicker.wear

import android.app.Application
import android.content.Context
import com.tritiumgaming.shared.data.wearable.repository.WearableRepository
import com.tritiumgaming.shared.data.wearable.repository.WearableRepositoryImpl
import com.tritiumgaming.shared.data.wearable.usecase.ObserveWearableOperationDataUseCase
import com.tritiumgaming.shared.data.wearable.usecase.SendWearableSanityMessageUseCase
import com.tritiumgaming.shared.data.wearable.usecase.SendWearableToggleMessageUseCase

class WearPETApplication : Application(), WearContainerProvider {

    lateinit var wearContainer: WearContainer

    override fun onCreate() {
        super.onCreate()
        wearContainer = WearContainer(this)
    }

    override fun provideWearContainer(): WearContainer = wearContainer
}

interface WearContainerProvider {
    fun provideWearContainer(): WearContainer
}

class WearContainer(context: Context) {
    private val wearableRepository: WearableRepository by lazy {
        WearableRepositoryImpl(context)
    }

    val observeWearableOperationDataUseCase = ObserveWearableOperationDataUseCase(wearableRepository)
    val sendWearableToggleMessageUseCase = SendWearableToggleMessageUseCase(wearableRepository)
    val sendWearableSanityMessageUseCase = SendWearableSanityMessageUseCase(wearableRepository)
}
