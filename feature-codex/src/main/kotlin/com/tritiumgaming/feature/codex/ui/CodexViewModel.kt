package com.tritiumgaming.feature.codex.ui

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.codex.app.container.CodexContainerProvider
import com.tritiumgaming.feature.codex.app.mappers.codex.toDrawableResource
import com.tritiumgaming.feature.codex.ui.catalog.PaginatorUiState
import com.tritiumgaming.feature.codex.ui.catalog.ScrollUiState
import com.tritiumgaming.feature.codex.ui.catalog.category.achievement.AchievementsCatalogUiState
import com.tritiumgaming.feature.codex.ui.catalog.category.equipment.EquipmentCatalogUiState
import com.tritiumgaming.feature.codex.ui.catalog.category.possession.PossessionsCatalogUiState
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources
import com.tritiumgaming.shared.data.codex.mappers.CodexResources
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources
import com.tritiumgaming.shared.data.codex.model.achievements.AchievementsType
import com.tritiumgaming.shared.data.codex.model.achievements.CodexAchievementsGroupItem
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentTypeTier
import com.tritiumgaming.shared.data.codex.model.possessions.CodexPossessionsGroupItem
import com.tritiumgaming.shared.data.codex.model.possessions.PossessionsType
import com.tritiumgaming.shared.data.codex.usecase.FetchAchievementTypesUseCase
import com.tritiumgaming.shared.data.codex.usecase.FetchEquipmentTypesUseCase
import com.tritiumgaming.shared.data.codex.usecase.FetchPossessionTypesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CodexViewModel(
    val fetchCodexEquipmentUseCase: FetchEquipmentTypesUseCase,
    val fetchCodexPossessionsUseCase: FetchPossessionTypesUseCase,
    val fetchCodexAchievementsUseCase: FetchAchievementTypesUseCase
): ViewModel() {

    /*sealed interface CatalogUiState {
        data class Equipment(
            val list: List<EquipmentType> = emptyList(),
            val selectedGroup: EquipmentType? = null,
            val selectedItem: EquipmentTypeTier? = null,
            @field:IntegerRes val icons: List<Int> = emptyList()
        ) : CatalogUiState

        data class Possessions(
            val list: List<PossessionsType> = emptyList(),
            val selectedGroup: PossessionsType? = null,
            val selectedItem: CodexPossessionsGroupItem? = null,
            @field:IntegerRes val icons: List<Int> = emptyList()
        ) : CatalogUiState

        data class Achievements(
            val list: List<AchievementsType> = emptyList(),
            val selectedGroup: AchievementsType? = null,
            val selectedItem: CodexAchievementsGroupItem? = null,
            @field:IntegerRes val icons: List<Int> = emptyList()
        ) : CatalogUiState
    }*/

    private val _equipmentUiState = MutableStateFlow(EquipmentCatalogUiState())
    val equipmentUiState = _equipmentUiState.asStateFlow()

    private val _possessionsUiState = MutableStateFlow(PossessionsCatalogUiState())
    val possessionsUiState = _possessionsUiState.asStateFlow()

    private val _achievementsUiState = MutableStateFlow(AchievementsCatalogUiState())
    val achievementsUiState = _achievementsUiState.asStateFlow()

    private val _scrollUiState = MutableStateFlow(ScrollUiState())
    val scrollUiState = _scrollUiState.asStateFlow()

    fun cacheCategory(category: CodexResources.Category) {
        viewModelScope.launch {
            when (category) {
                CodexResources.Category.EQUIPMENT -> cacheCodexEquipment()
                CodexResources.Category.POSSESSIONS -> cacheCodexPossessions()
                CodexResources.Category.ACHIEVEMENTS -> cacheCodexAchievements()
            }
        }
    }

    fun flushCategory(category: CodexResources.Category? = null) {
        viewModelScope.launch {
            when (category) {
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
    }

    @DrawableRes private fun getCategoryIcons(category: CodexResources.Category): List<Int> {
        return when(category) {
            CodexResources.Category.EQUIPMENT ->
                EquipmentResources.EquipmentIcon.entries.map { it.toDrawableResource() }
            CodexResources.Category.POSSESSIONS ->
                PossessionsResources.PossessionsIcon.entries.map { it.toDrawableResource() }
            CodexResources.Category.ACHIEVEMENTS ->
                AchievementsResources.AchievementIcon.entries.map { it.toDrawableResource() }
        }
    }

    private fun cacheCodexEquipment() {
        val result = fetchCodexEquipmentUseCase()

        try {
            val list = result.getOrThrow()
            val icons = getCategoryIcons(CodexResources.Category.EQUIPMENT)

            _equipmentUiState.update {
                it.copy(
                    list = list,
                    icons = icons
                )
            }
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun cacheCodexPossessions() {
        val result = fetchCodexPossessionsUseCase()

        try {
            val list = result.getOrThrow()
            val icons = getCategoryIcons(CodexResources.Category.POSSESSIONS)

            _possessionsUiState.update {
                it.copy(
                    list = list,
                    icons = icons
                )
            }
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun cacheCodexAchievements() {
        val result = fetchCodexAchievementsUseCase()

        try {
            val list = result.getOrThrow()
            val icons = getCategoryIcons(CodexResources.Category.ACHIEVEMENTS)

            _achievementsUiState.update {
                it.copy(
                    list = list,
                    icons = icons
                )
            }
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun flushCodexEquipment() {
        _equipmentUiState.value = EquipmentCatalogUiState()
    }

    private fun flushCodexPossessions() {
        _possessionsUiState.value = PossessionsCatalogUiState()
    }

    private fun flushCodexAchievements() {
        _achievementsUiState.value = AchievementsCatalogUiState()
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
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as CodexContainerProvider).provideCodexContainer()

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

}