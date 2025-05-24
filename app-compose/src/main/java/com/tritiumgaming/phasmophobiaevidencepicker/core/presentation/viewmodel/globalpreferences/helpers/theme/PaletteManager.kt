package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.mapper.toPair
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.market.model.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference.FindNextAvailablePaletteUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference.GetPaletteByUUIDUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference.GetAvailablePalettesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalDefaultPalette
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PaletteManager(
    private val datastore: PaletteDatastore,
    /*private val setupPaletteUseCase: SetupPaletteUseCase,
    private val initFlowPaletteUseCase: InitFlowPaletteUseCase,*/
    private val getPalettesUseCase: GetAvailablePalettesUseCase,
    private val getPaletteByUUIDUseCase: GetPaletteByUUIDUseCase,
    private val findNextAvailablePaletteUseCase: FindNextAvailablePaletteUseCase
): ThemeManager {

    override val defaultUUID: String = LocalDefaultPalette.uuid

    private val _currentUUID : MutableStateFlow<String> = MutableStateFlow(defaultUUID)
    val currentUUID = _currentUUID.asStateFlow()

    override suspend fun setCurrentUUID(uuid: String) {
        _currentUUID.update { uuid }
        saveCurrentUUID()
        Log.d("Settings", "$uuid -> ${_currentUUID.value} == ${this.currentUUID.value}")
    }

    override suspend fun saveCurrentUUID() {
        Log.d("Settings", "Attempting save palette.")
        datastore.savePalette(currentUUID.value)
    }

    private val _palettes = MutableStateFlow(mapOf<String, MarketPalette>())
    val palettes = _palettes.asStateFlow()

    suspend fun fetchPalettes() {
        val mergedModels = getPalettesUseCase()

        _palettes.update { mergedModels.toPair() }
    }

    fun getPaletteByUUID(uuid: String): ExtendedPalette =
        getPaletteByUUIDUseCase(palettes.value, uuid, LocalDefaultPalette.palette)

    fun findNextAvailable(
        currentUUID: StateFlow<String>,
        direction: IncrementDirection
    ): String = findNextAvailablePaletteUseCase(palettes.value, currentUUID.value, direction)

    fun findNextAvailable(
        direction: IncrementDirection
    ): String = findNextAvailable(currentUUID, direction)

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

}