package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.mapper.toPair
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.market.model.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.FetchPalettesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.GetPaletteByUUIDUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.usecase.FindNextAvailablePaletteUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalDefaultPalette
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PaletteManager(
    private val repository: PaletteRepository,
    private val datastore: PaletteDatastore,
    private val fetchPalettesUseCase: FetchPalettesUseCase,
    private val getPalleteByUUIDUseCase: GetPaletteByUUIDUseCase,
    private val findNextAvailablePaletteUseCase: FindNextAvailablePaletteUseCase
) : AThemeManager(
    defaultUUID = LocalDefaultPalette.uuid,
) {

    private val _palettes = MutableStateFlow(mapOf<String, MarketPalette>())
    val palettes = _palettes.asStateFlow()

    suspend fun fetchPalettes() {
        val mergedModels = fetchPalettesUseCase.invoke()

        _palettes.update { mergedModels.toPair() }
    }

    fun getPaletteByUUID(uuid: String): ExtendedPalette =
        getPalleteByUUIDUseCase.invoke(palettes.value, uuid, LocalDefaultPalette.palette)

    fun findNextAvailable(
        direction: IncrementDirection
    ): String = findNextAvailable(currentUUID, direction)

    fun findNextAvailable(
        currentUUID: StateFlow<String>,
        direction: IncrementDirection
    ): String = findNextAvailablePaletteUseCase.invoke(palettes.value, currentUUID.value, direction)

    fun initialSetupEvent() {
        datastore.initialSetupEvent()
    }

    suspend fun initFlow() {
        datastore.flow.collect { preferences ->
            _currentUUID.update {
                preferences.uuid.ifBlank { defaultUUID }
            }
            Log.d("Color", "Collecting from flow:\n\tID -> ${currentUUID.value}")
        }
    }

    override suspend fun saveCurrentUUID() {
        Log.d("Settings", "Attempting save palette.")
        datastore.savePalette(currentUUID.value)
    }

}