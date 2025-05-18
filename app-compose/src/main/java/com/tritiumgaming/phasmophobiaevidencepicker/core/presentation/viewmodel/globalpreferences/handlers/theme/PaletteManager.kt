package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.theme

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.market.model.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.toPair
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalDefaultPalette
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PaletteManager(
    private val repository: PaletteRepository,
    private val datastore: PaletteDatastore
) : AThemeManager(
    defaultUUID = LocalDefaultPalette.uuid,
) {

    private val _palettes = MutableStateFlow(mapOf<String, PaletteEntity>())
    var palettes = _palettes.asStateFlow()

    suspend fun fetchPalettes() {
        val localPalettes: List<PaletteEntity> = repository.getLocalPalettes()
        val remotePalettes: List<PaletteEntity> = repository.getRemotePalettes()

        val mergedModels = remotePalettes.fold(localPalettes) { localList, remoteEntity ->
            localList.map { localEntity ->
                if (localEntity.uuid == remoteEntity.uuid) localEntity.copy(
                    uuid = localEntity.uuid,
                    name = remoteEntity.name,
                    group = remoteEntity.group,
                    buyCredits = remoteEntity.buyCredits,
                    priority = remoteEntity.priority,
                    unlocked = remoteEntity.unlocked,
                    palette = remoteEntity.palette ?: ExtendedPalette()
                )
                else localEntity
            }
        }
        Log.d("Palette", "Fetched ${mergedModels.size} palettes")
        mergedModels.forEach {
            Log.d("Palette", "Fetched $it")
        }

        _palettes.update { mergedModels.toPair() }
    }

    fun getPaletteByUUID(uuid: String): ExtendedPalette =
        palettes.value[uuid]?.palette ?: ExtendedPalette()

    fun findNextAvailable(
        direction: IncrementDirection
    ): String {
        return findNextAvailable(currentUUID, direction)
    }

    fun findNextAvailable(
        currentUUID: StateFlow<String>,
        direction: IncrementDirection
    ): String {

        Log.d("Settings", "${currentUUID.value} ${palettes.value.size}")
        if(palettes.value.isEmpty()) return ""

        val filtered = palettes.value
            .filter {
                it.value.palette != null &&
                        it.value.isUnlocked == true
            }

        Log.d("Settings", "Filtered: ${filtered.size}")
        if(filtered.isEmpty()) return ""


        val list = filtered.keys.toList()
        val currentIndex = list.indexOf(currentUUID.value)

        var increment = currentIndex + direction.value
        if(increment >= list.size) increment = 0
        if(increment < 0) increment = list.size - 1

        Log.d("Settings", "Move: $currentIndex $increment $direction")

        return list[increment]
    }

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