package com.tritiumgaming.phasmophobiaevidencepicker.wear.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.wear.WearContainerProvider
import com.tritiumgaming.shared.data.wearable.model.WearableOperationData
import com.tritiumgaming.shared.data.wearable.usecase.ObserveWearableOperationDataUseCase
import com.tritiumgaming.shared.data.wearable.usecase.SendWearableToggleMessageUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WearableViewModel(
    observeWearableOperationDataUseCase: ObserveWearableOperationDataUseCase,
    private val sendWearableToggleMessageUseCase: SendWearableToggleMessageUseCase
) : ViewModel() {

    val uiState: StateFlow<WearableOperationData> = observeWearableOperationDataUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WearableOperationData()
        )

    fun toggleEvidence(evidenceId: String, currentState: String) {
        val nextState = when (currentState) {
            "NEUTRAL" -> "POSITIVE"
            "POSITIVE" -> "NEGATIVE"
            else -> "NEUTRAL"
        }
        
        viewModelScope.launch {
            sendWearableToggleMessageUseCase(evidenceId, nextState)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as WearContainerProvider).provideWearContainer()
                
                WearableViewModel(
                    observeWearableOperationDataUseCase = container.observeWearableOperationDataUseCase,
                    sendWearableToggleMessageUseCase = container.sendWearableToggleMessageUseCase
                )
            }
        }
    }
}
