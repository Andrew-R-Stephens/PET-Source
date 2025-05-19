package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper.toPair
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.market.model.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.TypographyDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalDefaultTypography
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TypographyManager(
    private val repository: TypographyRepository,
    private val datastore: TypographyDatastore
) : AThemeManager(
    defaultUUID = LocalDefaultTypography.uuid
) {

    private val _typographies = MutableStateFlow(mapOf<String, MarketTypography>())
    var typographies = _typographies.asStateFlow()

    suspend fun fetchTypographies() {
        val localPalettes: List<MarketTypography> = repository.getLocalTypographies()
        val remotePalettes: List<MarketTypography> = repository.getRemoteTypographies()

        val mergedModels = remotePalettes.fold(localPalettes) { localList, remoteEntity ->
            localList.map { localEntity ->
                if (localEntity.uuid == remoteEntity.uuid) localEntity.copy(
                    uuid = localEntity.uuid,
                    name = remoteEntity.name,
                    group = remoteEntity.group,
                    buyCredits = remoteEntity.buyCredits,
                    priority = remoteEntity.priority,
                    unlocked = remoteEntity.unlocked,
                    typography = remoteEntity.typography ?: ExtendedTypography()
                )
                else localEntity
            }
        }
        Log.d("Typography", "Fetched ${mergedModels.size} typography")
        mergedModels.forEach {
            Log.d("Typography", "Fetched $it")
        }

        _typographies.update { mergedModels.toPair() }
    }

    fun getTypographyByUUID(uuid: String): ExtendedTypography =
        typographies.value[uuid]?.typography ?: ExtendedTypography()

    fun findNextAvailable(
        direction: IncrementDirection
    ): String {
        return findNextAvailable(currentUUID, direction)
    }

    fun findNextAvailable(
        currentUUID: StateFlow<String>,
        direction: IncrementDirection
    ): String {

        Log.d("Settings", "${currentUUID.value} ${typographies.value.size}")
        if(typographies.value.isEmpty()) return ""

        val filtered = typographies.value
            .filter {
                it.value.typography != null &&
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
        Log.d("Settings", "Attempting save typography.")
        datastore.saveTypography(currentUUID.value)
    }

}
