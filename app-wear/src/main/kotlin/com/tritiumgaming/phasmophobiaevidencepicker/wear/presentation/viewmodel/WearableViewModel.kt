package com.tritiumgaming.phasmophobiaevidencepicker.wear.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.wear.WearContainerProvider
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources.PaletteType
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.shared.data.operation.model.EvidenceValidationType
import com.tritiumgaming.shared.data.wearable.model.WearableInvestigationData
import com.tritiumgaming.shared.data.wearable.model.WearableOperationData
import com.tritiumgaming.shared.data.wearable.usecase.ObserveWearableOperationDataUseCase
import com.tritiumgaming.shared.data.wearable.usecase.SendWearableSanityMessageUseCase
import com.tritiumgaming.shared.data.wearable.usecase.SendWearableToggleMessageUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WearableViewModel(
    observeWearableOperationDataUseCase: ObserveWearableOperationDataUseCase,
    private val sendWearableToggleMessageUseCase: SendWearableToggleMessageUseCase,
    private val sendWearableSanityMessageUseCase: SendWearableSanityMessageUseCase
) : ViewModel() {

    val uiState: StateFlow<WearableOperationData> = observeWearableOperationDataUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WearableOperationData(
                investigationData = WearableInvestigationData(
                    mapName = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
                    difficultyName = DifficultyResources.DifficultyType.AMATEUR,
                    setupTimeRemaining = 0L,
                    sanityLevel = 1f,
                    evidenceStates = emptyList()
                ),
                palette = PaletteType.CLASSIC,
                typography = TypographyType.CLASSIC
            )
        )

    fun toggleEvidence(
        evidenceType: EvidenceType,
        currentState: EvidenceValidationType
    ) {
        val ordinal = (currentState.ordinal + 1) % EvidenceValidationType.entries.size
        
        viewModelScope.launch {
            sendWearableToggleMessageUseCase(
                evidenceType,
                EvidenceValidationType.entries[ordinal]
            )
        }
    }

    fun updateSanity(level: Float) {
        viewModelScope.launch {
            sendWearableSanityMessageUseCase(level)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as WearContainerProvider).provideWearContainer()
                
                WearableViewModel(
                    observeWearableOperationDataUseCase = container.observeWearableOperationDataUseCase,
                    sendWearableToggleMessageUseCase = container.sendWearableToggleMessageUseCase,
                    sendWearableSanityMessageUseCase = container.sendWearableSanityMessageUseCase
                )
            }
        }
    }
}
