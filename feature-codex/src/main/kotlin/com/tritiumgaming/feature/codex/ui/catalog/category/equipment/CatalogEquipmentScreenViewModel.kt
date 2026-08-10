package com.tritiumgaming.feature.codex.ui.catalog.category.equipment

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
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentTypeTier
import com.tritiumgaming.shared.data.codex.usecase.FetchEquipmentTypesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatalogEquipmentScreenViewModel(
    val fetchCodexEquipmentUseCase: FetchEquipmentTypesUseCase,
): ViewModel() {

    private val _categoryCache = MutableStateFlow(CategoryCache())
    private val categoryCache = _categoryCache.asStateFlow()

    private val _catalogUiState = MutableStateFlow(
        CatalogCategoryUiState(
            catalog = CatalogCategory.Equipment()
        )
    )
    val catalogUiState = _catalogUiState.asStateFlow()

    private val _displayUiState =
        MutableStateFlow<CatalogDisplayUiState>(CatalogDisplayUiState.Equipment())
    val displayUiState = _displayUiState.asStateFlow()

    private val _scrollUiState = MutableStateFlow(ScrollUiState())
    val scrollUiState = _scrollUiState.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            cacheCodexEquipment()

            loadCodexEquipment()
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

    private fun flushCodexEquipment() {
        _catalogUiState.update {
            it.copy(catalog = CatalogCategory.Equipment())
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

                val fetchCodexEquipmentUseCase = container.fetchCodexEquipmentUseCase

                CatalogEquipmentScreenViewModel(
                    fetchCodexEquipmentUseCase = fetchCodexEquipmentUseCase,
                )
            }
        }
    }

    private data class CategoryCache(
        val equipment: List<EquipmentType> = emptyList(),
    )

}