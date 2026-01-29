package com.tritiumgaming.feature.codex.ui

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.codex.app.container.CodexContainerProvider
import com.tritiumgaming.feature.codex.app.mappers.codex.toDrawableResource
import com.tritiumgaming.feature.codex.ui.catalog.ScrollUiState
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogCategory
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogCategoryUiState
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogDisplayUiState
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

    private val _catalogUiState = MutableStateFlow(CatalogCategoryUiState())
    val catalogUiState = _catalogUiState.asStateFlow()

    private val _displayUiState =
        MutableStateFlow<CatalogDisplayUiState>(CatalogDisplayUiState.None())
    val displayUiState = _displayUiState.asStateFlow()

    private val _scrollUiState = MutableStateFlow(ScrollUiState())
    val scrollUiState = _scrollUiState.asStateFlow()

    fun cacheCategory(category: CodexResources.Category) {
        viewModelScope.launch {
            when (category) {
                CodexResources.Category.EQUIPMENT -> cacheCodexEquipment()
                CodexResources.Category.POSSESSIONS -> cacheCodexPossessions()
                CodexResources.Category.ACHIEVEMENTS -> cacheCodexAchievements()
                else -> { /*DO NOTHING*/ }
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
            else -> emptyList()
        }
    }

    private fun cacheCodexEquipment() {
        val result = fetchCodexEquipmentUseCase()

        try {
            val list = result.getOrThrow()
            val icons = getCategoryIcons(CodexResources.Category.EQUIPMENT)

            _catalogUiState.update {
                it.copy(
                    equipment = CatalogCategory.Equipment(
                        list = list,
                        icons = icons
                    )
                )
            }

        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun cacheCodexPossessions() {
        val result = fetchCodexPossessionsUseCase()

        try {
            val list = result.getOrThrow()
            val icons = getCategoryIcons(CodexResources.Category.POSSESSIONS)

            _catalogUiState.update {
                it.copy(
                    possessions = CatalogCategory.Possessions(
                        list = list,
                        icons = icons
                    )
                )
            }

        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun cacheCodexAchievements() {
        val result = fetchCodexAchievementsUseCase()

        try {
            val list = result.getOrThrow()
            val icons = getCategoryIcons(CodexResources.Category.ACHIEVEMENTS)

            _catalogUiState.update {
                it.copy(
                    achievements = CatalogCategory.Achievements(
                        list = list,
                        icons = icons
                    )
                )
            }

        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun flushCodexEquipment() {
        _catalogUiState.update {
            it.copy(equipment = CatalogCategory.Equipment())
        }
    }

    private fun flushCodexPossessions() {
        _catalogUiState.update {
            it.copy(possessions = CatalogCategory.Possessions())
        }
    }

    private fun flushCodexAchievements() {
        _catalogUiState.update {
            it.copy(achievements = CatalogCategory.Achievements())
        }
    }

    fun setSelectedEquipment(
        group: EquipmentType? = null,
        item: EquipmentTypeTier? = null
    ) {
        _displayUiState.update {
            CatalogDisplayUiState.Equipment(
                selectedGroup = group,
                selectedItem = item
            )
        }
    }

    fun setSelectedPossession(
        group: PossessionsType? = null,
        item: CodexPossessionsGroupItem? = null
    ) {
        _displayUiState.update {
            CatalogDisplayUiState.Possessions(
                selectedGroup = group,
                selectedItem = item
            )
        }
    }

    fun setSelectedAchievement(
        group: AchievementsType? = null,
        item: CodexAchievementsGroupItem? = null
    ) {
        _displayUiState.update {
            CatalogDisplayUiState.Achievements(
                selectedGroup = group,
                selectedItem = item
            )
        }
    }

    fun clearDisplay() {
        _displayUiState.update {
            CatalogDisplayUiState.None()
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