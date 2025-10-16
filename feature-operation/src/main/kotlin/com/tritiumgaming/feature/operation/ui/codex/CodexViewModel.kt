package com.tritiumgaming.feature.operation.ui.codex

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.operation.app.container.OperationContainerProvider
import com.tritiumgaming.feature.operation.app.mappers.codex.toDrawableResource
import com.tritiumgaming.shared.operation.domain.codex.mappers.AchievementsResources
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexResources
import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources
import com.tritiumgaming.shared.operation.domain.codex.mappers.PossessionsResources
import com.tritiumgaming.shared.operation.domain.codex.model.achievements.AchievementsType
import com.tritiumgaming.shared.operation.domain.codex.model.achievements.CodexAchievementsGroupItem
import com.tritiumgaming.shared.operation.domain.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.operation.domain.codex.model.equipment.EquipmentTypeTier
import com.tritiumgaming.shared.operation.domain.codex.model.possessions.CodexPossessionsGroupItem
import com.tritiumgaming.shared.operation.domain.codex.model.possessions.PossessionsType
import com.tritiumgaming.shared.operation.domain.codex.usecase.FetchAchievementTypesUseCase
import com.tritiumgaming.shared.operation.domain.codex.usecase.FetchPossessionTypesUseCase
import com.tritiumgaming.shared.operation.domain.codex.usecase.FetchEquipmentTypesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CodexViewModel(
    val fetchCodexEquipmentUseCase: FetchEquipmentTypesUseCase,
    val fetchCodexPossessionsUseCase: FetchPossessionTypesUseCase,
    val fetchCodexAchievementsUseCase: FetchAchievementTypesUseCase
): ViewModel() {

    private val _equipmentUiState = MutableStateFlow(CodexEquipmentUiState())
    val equipmentUiState = _equipmentUiState.asStateFlow()

    private val _possessionsUiState = MutableStateFlow(CodexPossessionUiState())
    val possessionsUiState = _possessionsUiState.asStateFlow()

    private val _achievementsUiState = MutableStateFlow(CodexAchievementUiState())
    val achievementsUiState = _achievementsUiState.asStateFlow()

    private val _scrollUiState = MutableStateFlow(ScrollUiState())
    val scrollUiState = _scrollUiState.asStateFlow()

    fun cacheCategory(category: CodexResources.Category) {
        when(category) {
            CodexResources.Category.EQUIPMENT -> cacheCodexEquipment()
            CodexResources.Category.POSSESSIONS -> cacheCodexPossessions()
            CodexResources.Category.ACHIEVEMENTS -> cacheCodexAchievements()
        }
    }

    fun flushCategory(category: CodexResources.Category? = null) {
        when(category) {
            CodexResources.Category.EQUIPMENT -> flushCodexEquipment()
            CodexResources.Category.POSSESSIONS -> flushCodexPossessions()
            CodexResources.Category.ACHIEVEMENTS -> flushCodexAchievements()
            else -> {
                flushCodexEquipment()
                flushCodexPossessions()
                flushCodexAchievements()
            }
        }
    }

    @DrawableRes fun getCategoryIcons(category: CodexResources.Category): List<Int> {
        return when(category) {
            CodexResources.Category.EQUIPMENT -> EquipmentResources.EquipmentIcon.entries.map { it.toDrawableResource() }
            CodexResources.Category.POSSESSIONS -> PossessionsResources.PossessionsIcon.entries.map { it.toDrawableResource() }
            CodexResources.Category.ACHIEVEMENTS -> AchievementsResources.AchievementIcon.entries.map { it.toDrawableResource() }
        }
    }

    private fun cacheCodexEquipment() {
        val result = fetchCodexEquipmentUseCase()

        try {
            val list = result.getOrThrow()

            _equipmentUiState.update {
                it.copy(
                    list = list
                )
            }
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun cacheCodexPossessions() {
        val result = fetchCodexPossessionsUseCase()

        try {
            val list = result.getOrThrow()

            _possessionsUiState.update {
                it.copy(
                    list = list
                )
            }
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun cacheCodexAchievements() {
        val result = fetchCodexAchievementsUseCase()

        try {
            val list = result.getOrThrow()

            _achievementsUiState.update {
                it.copy(
                    list = list
                )
            }
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun flushCodexEquipment() {
        _equipmentUiState.value = CodexEquipmentUiState()
    }

    private fun flushCodexPossessions() {
        _possessionsUiState.value = CodexPossessionUiState()
    }

    private fun flushCodexAchievements() {
        _achievementsUiState.value = CodexAchievementUiState()
    }

    fun setSelectedEquipment(
        group: EquipmentType? = null,
        item: EquipmentTypeTier? = null
    ) {
        _equipmentUiState.update {
            it.copy(
                selectedGroup = group,
                selectedItem = item
            )
        }
    }

    fun setSelectedPossession(
        group: PossessionsType? = null,
        item: CodexPossessionsGroupItem? = null
    ) {
        _possessionsUiState.update {
            it.copy(
                selectedGroup = group,
                selectedItem = item
            )
        }
    }

    fun setSelectedAchievement(
        group: AchievementsType? = null,
        item: CodexAchievementsGroupItem? = null
    ) {
        _achievementsUiState.update {
            it.copy(
                selectedGroup = group,
                selectedItem = item
            )
        }
    }

    fun setScrollOffset(offset: Float? = null, index: Int? = null) {
        _scrollUiState.update {
            it.copy(
                offset = offset ?: it.offset,
                itemIndex = index ?: it.itemIndex
            )
        }
        Log.d("CodexViewModel", "setScrollOffset: $offset")
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]
                val container = (application as OperationContainerProvider).provideOperationContainer()

                val fetchCodexEquipmentUseCase = container.fetchCodexEquipmentUseCase
                val fetchCodexPossessionsUseCase = container.fetchCodexPossessionsUseCase
                val fetchCodexAchievementsUseCase = container.fetchCodexAchievementsUseCase
                
                CodexViewModel(
                    fetchCodexEquipmentUseCase = fetchCodexEquipmentUseCase,
                    fetchCodexPossessionsUseCase = fetchCodexPossessionsUseCase,
                    fetchCodexAchievementsUseCase = fetchCodexAchievementsUseCase
                )
            }
        }
    }

    data class CodexEquipmentUiState(
        val list: List<EquipmentType> = emptyList(),
        val selectedGroup: EquipmentType? = null,
        val selectedItem: EquipmentTypeTier? = null,
    )

    data class CodexPossessionUiState(
        val list: List<PossessionsType> = emptyList(),
        val selectedGroup: PossessionsType? = null,
        val selectedItem: CodexPossessionsGroupItem? = null,
    )

    data class CodexAchievementUiState(
        val list: List<AchievementsType> = emptyList(),
        val selectedGroup: AchievementsType? = null,
        val selectedItem: CodexAchievementsGroupItem? = null,
    )

    data class ScrollUiState(
        val offset: Float = 0f,
        val itemIndex: Int = 0
    )

}