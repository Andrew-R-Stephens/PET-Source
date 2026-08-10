package com.tritiumgaming.feature.codex.ui.catalog.category.possession

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
import com.tritiumgaming.shared.data.codex.model.possessions.CodexPossessionsGroupItem
import com.tritiumgaming.shared.data.codex.model.possessions.PossessionsType
import com.tritiumgaming.shared.data.codex.usecase.FetchPossessionTypesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatalogPossessionScreenViewModel(
    val fetchCodexPossessionsUseCase: FetchPossessionTypesUseCase
): ViewModel() {

    private val _categoryCache = MutableStateFlow(CategoryCache())
    private val categoryCache = _categoryCache.asStateFlow()

    private val _catalogUiState = MutableStateFlow(
        CatalogCategoryUiState(
            catalog = CatalogCategory.Possessions()
        )
    )
    val catalogUiState = _catalogUiState.asStateFlow()

    private val _displayUiState =
        MutableStateFlow<CatalogDisplayUiState>(CatalogDisplayUiState.Possessions())
    val displayUiState = _displayUiState.asStateFlow()

    private val _scrollUiState = MutableStateFlow(ScrollUiState())
    val scrollUiState = _scrollUiState.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            cacheCodexPossessions()

            loadCodexPossessions()
        }
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

    private fun flushCodexPossessions() {
        _catalogUiState.update {
            it.copy(catalog = CatalogCategory.Possessions())
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

                val fetchCodexPossessionsUseCase = container.fetchCodexPossessionsUseCase

                CatalogPossessionScreenViewModel(
                    fetchCodexPossessionsUseCase = fetchCodexPossessionsUseCase,
                )
            }
        }
    }

    private data class CategoryCache(
        val possessions: List<PossessionsType> = emptyList()
    )

}