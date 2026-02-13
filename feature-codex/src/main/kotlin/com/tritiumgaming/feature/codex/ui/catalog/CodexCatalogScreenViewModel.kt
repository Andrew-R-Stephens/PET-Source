package com.tritiumgaming.feature.codex.ui.catalog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.codex.app.container.CodexContainerProvider
import com.tritiumgaming.feature.codex.app.mappers.codex.toDrawableResource
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogCategory
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogCategoryUiState
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogDisplayUiState
import com.tritiumgaming.shared.data.codex.mappers.CodexResources
import com.tritiumgaming.shared.data.codex.model.achievements.AchievementsType
import com.tritiumgaming.shared.data.codex.model.achievements.CodexAchievementsGroupItem
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentTypeTier
import com.tritiumgaming.shared.data.codex.model.possessions.CodexPossessionsGroupItem
import com.tritiumgaming.shared.data.codex.model.possessions.PossessionsType
import com.tritiumgaming.shared.data.codex.usecase.FetchAchievementTypesUseCase
import com.tritiumgaming.shared.data.codex.usecase.FetchEquipmentTypesUseCase
import com.tritiumgaming.shared.data.codex.usecase.FetchPossessionTypesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CodexCatalogScreenViewModel(
    val fetchCodexEquipmentUseCase: FetchEquipmentTypesUseCase,
    val fetchCodexPossessionsUseCase: FetchPossessionTypesUseCase,
    val fetchCodexAchievementsUseCase: FetchAchievementTypesUseCase
): ViewModel() {

    private val _categoryCache = MutableStateFlow(CategoryCache())
    private val categoryCache = _categoryCache.asStateFlow()

    private val _catalogUiState = MutableStateFlow(
        CatalogCategoryUiState(
            catalog = CatalogCategory.None()
        )
    )
    val catalogUiState = _catalogUiState.asStateFlow()

    private val _displayUiState =
        MutableStateFlow<CatalogDisplayUiState>(CatalogDisplayUiState.None())
    val displayUiState = _displayUiState.asStateFlow()

    private val _scrollUiState = MutableStateFlow(ScrollUiState())
    val scrollUiState = _scrollUiState.asStateFlow()

    fun loadCategory(category: CodexResources.Category) {
        if (this.catalogUiState.value.catalog.category == category) return

        viewModelScope.launch {
            when (category) {
                CodexResources.Category.EQUIPMENT -> loadCodexEquipment()
                CodexResources.Category.POSSESSIONS -> loadCodexPossessions()
                CodexResources.Category.ACHIEVEMENTS -> loadCodexAchievements()
                else -> { /*DO NOTHING*/ }
            }
        }
    }

    fun cacheAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            cacheCodexEquipment()
            cacheCodexPossessions()
            cacheCodexAchievements()
        }
    }

     private fun cacheCodexEquipment() {

         if(categoryCache.value.equipment.isNotEmpty()) return
         val result = fetchCodexEquipmentUseCase()

        try {
            val list = result.getOrThrow()

            _categoryCache.update {
                it.copy(
                    equipment = list
                )
            }

        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun cacheCodexPossessions() {
        if(categoryCache.value.possessions.isNotEmpty()) return
        val result = fetchCodexPossessionsUseCase()

        try {
            val list = result.getOrThrow()

            _categoryCache.update {
                it.copy(
                    possessions = list
                )
            }

        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun cacheCodexAchievements() {
        if(categoryCache.value.achievements.isNotEmpty()) return
        val result = fetchCodexAchievementsUseCase()

        try {
            val list = result.getOrThrow()

            _categoryCache.update {
                it.copy(
                    achievements = list
                )
            }

        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun loadCodexEquipment() {
        try {
            val list = categoryCache.value.equipment

            _catalogUiState.update {
                it.copy(
                    catalog = CatalogCategory.Equipment(
                        list = list,
                        icons = list.map { item -> item.icon.toDrawableResource() }
                    )
                )
            }

        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun loadCodexPossessions() {
        try {
            val list = categoryCache.value.possessions

            _catalogUiState.update {
                it.copy(
                    catalog = CatalogCategory.Possessions(
                        list = list,
                        icons = list.map { item -> item.icon.toDrawableResource() }
                    )
                )
            }

        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun loadCodexAchievements() {
        try {
            val list = categoryCache.value.achievements

            _catalogUiState.update {
                it.copy(
                    catalog = CatalogCategory.Achievements(
                        list = list,
                        icons = list.map { item -> item.icon.toDrawableResource() }
                    )
                )
            }

        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun flushCodexEquipment() {
        _catalogUiState.update {
            it.copy(catalog = CatalogCategory.Equipment())
        }
    }

    private fun flushCodexPossessions() {
        _catalogUiState.update {
            it.copy(catalog = CatalogCategory.Possessions())
        }
    }

    private fun flushCodexAchievements() {
        _catalogUiState.update {
            it.copy(catalog = CatalogCategory.Achievements())
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

    init {
        cacheAllCategories()
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as CodexContainerProvider).provideCodexContainer()

                val fetchCodexEquipmentUseCase = container.fetchCodexEquipmentUseCase
                val fetchCodexPossessionsUseCase = container.fetchCodexPossessionsUseCase
                val fetchCodexAchievementsUseCase = container.fetchCodexAchievementsUseCase

                CodexCatalogScreenViewModel(
                    fetchCodexEquipmentUseCase = fetchCodexEquipmentUseCase,
                    fetchCodexPossessionsUseCase = fetchCodexPossessionsUseCase,
                    fetchCodexAchievementsUseCase = fetchCodexAchievementsUseCase
                )
            }
        }
    }

    private data class CategoryCache(
        val equipment: List<EquipmentType> = emptyList(),
        val possessions: List<PossessionsType> = emptyList(),
        val achievements: List<AchievementsType> = emptyList()
    )

}