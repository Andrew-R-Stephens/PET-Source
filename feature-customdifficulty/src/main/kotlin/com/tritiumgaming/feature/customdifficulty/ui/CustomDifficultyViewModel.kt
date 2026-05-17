package com.tritiumgaming.feature.customdifficulty.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.customdifficulty.app.container.CustomDifficultyContainerProvider
import com.tritiumgaming.shared.data.customdifficulty.model.CustomDifficultyModel
import com.tritiumgaming.shared.data.customdifficulty.usecase.GetCustomDifficultiesUseCase
import com.tritiumgaming.shared.data.customdifficulty.usecase.UpdateCustomDifficultyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

data class CustomDifficultyUiState(
    val difficulties: List<CustomDifficultyModel> = emptyList(),
    val selectedDifficulty: CustomDifficultyModel? = null,
    val isSaving: Boolean = false,
    val hasChanges: Boolean = false
)

class CustomDifficultyViewModel(
    private val getCustomDifficultiesUseCase: GetCustomDifficultiesUseCase,
    private val updateCustomDifficultyUseCase: UpdateCustomDifficultyUseCase
): ViewModel() {

    private val _customDifficulties = getCustomDifficultiesUseCase().map {
        it.getOrDefault(emptyList())
    }

    private val _selectedDifficulty = MutableStateFlow<CustomDifficultyModel?>(null)
    private val _isSaving = MutableStateFlow(false)

    val uiState: StateFlow<CustomDifficultyUiState> = combine(
        _customDifficulties,
        _selectedDifficulty,
        _isSaving
    ) { difficulties, selected, isSaving ->

        val currentSelected = selected ?: difficulties.firstOrNull()
        val original = difficulties.find { it.id == currentSelected?.id }
        val hasChanges = currentSelected != null && original != null && currentSelected != original

        CustomDifficultyUiState(
            difficulties = difficulties,
            selectedDifficulty = currentSelected,
            isSaving = isSaving,
            hasChanges = hasChanges
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CustomDifficultyUiState()
    )

    fun selectDifficulty(difficulty: CustomDifficultyModel) {
        _selectedDifficulty.value = difficulty
    }

    fun updateSelectedDifficulty(transform: (CustomDifficultyModel) -> CustomDifficultyModel) {
        _selectedDifficulty.update { current ->
            val target = current ?: uiState.value.selectedDifficulty
            target?.let(transform)
        }
    }

    fun saveChanges() {
        val difficulty = _selectedDifficulty.value ?: uiState.value.selectedDifficulty ?: return
        viewModelScope.launch {
            _isSaving.value = true
            updateCustomDifficultyUseCase(difficulty)
            _isSaving.value = false
        }
    }

    fun revertChanges() {
        val current = _selectedDifficulty.value ?: uiState.value.selectedDifficulty ?: return
        val original = uiState.value.difficulties.find { it.id == current.id }
        if (original != null) {
            _selectedDifficulty.value = original.copy(name = null)
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as CustomDifficultyContainerProvider).provideCustomDifficultyContainer()

                CustomDifficultyViewModel(
                    getCustomDifficultiesUseCase = container.getCustomDifficultiesUseCase,
                    updateCustomDifficultyUseCase = container.updateCustomDifficultyUseCase
                )
            }
        }
    }

}
