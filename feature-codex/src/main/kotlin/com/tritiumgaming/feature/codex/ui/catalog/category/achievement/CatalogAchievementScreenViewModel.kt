package com.tritiumgaming.feature.codex.ui.catalog.category.achievement

import android.util.Log
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
import com.tritiumgaming.shared.data.codex.model.achievements.AchievementsType
import com.tritiumgaming.shared.data.codex.model.achievements.CodexAchievementsGroupItem
import com.tritiumgaming.shared.data.codex.usecase.FetchAchievementTypesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatalogAchievementScreenViewModel(
    val fetchCodexAchievementsUseCase: FetchAchievementTypesUseCase
): ViewModel() {

    private val _categoryCache = MutableStateFlow(CategoryCache())
    private val categoryCache = _categoryCache.asStateFlow()

    private val _catalogUiState = MutableStateFlow(
        CatalogCategoryUiState(
            catalog = CatalogCategory.Achievements()
        )
    )
    val catalogUiState = _catalogUiState.asStateFlow()

    private val _displayUiState =
        MutableStateFlow<CatalogDisplayUiState>(CatalogDisplayUiState.Achievements())
    val displayUiState = _displayUiState.asStateFlow()

    private val _scrollUiState = MutableStateFlow(ScrollUiState())
    val scrollUiState = _scrollUiState.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            cacheCodexAchievements()

            loadCodexAchievements()
        }
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
        load()
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as CodexContainerProvider).provideCodexContainer()

                val fetchCodexAchievementsUseCase = container.fetchCodexAchievementsUseCase

                CatalogAchievementScreenViewModel(
                    fetchCodexAchievementsUseCase = fetchCodexAchievementsUseCase
                )
            }
        }
    }

    private data class CategoryCache(
        val achievements: List<AchievementsType> = emptyList()
    )

}