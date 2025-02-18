package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.theming.subsets

import android.util.Log
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.TypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.theming.AThemeHandler
import kotlinx.coroutines.flow.update

class TypographyHandler(
    private val repository: TypographyRepository
) : AThemeHandler(
    defaultUUID = repository.defaultTypography.uuid
) {

    fun initialSetupEvent() {
        liveData {
            emit(repository.fetchInitialPreferences())
        }
    }

    suspend fun initFlow() {
        repository.flow.collect { preferences ->
            _currentUUID.update {
                preferences.uuid.ifBlank { defaultUUID }
            }
            Log.d("Color", "Collecting from flow:\n\tID -> ${currentUUID.value}")
        }
    }

    override fun findNextAvailable(
        direction: IncrementDirection,
        uuid: String?
    ): String {

        Log.d("Settings", "$uuid ${repository.allTypographies.value.size}")
        if(repository.allTypographies.value.isEmpty()) return ""

        val filtered = repository.allTypographies.value
            .filter {
                it.value.marketTypographyEntity == null
                        || it.value.marketTypographyEntity?.unlocked == true
            }

        Log.d("Settings", "Filtered: ${filtered.size}")
        if(filtered.isEmpty()) return ""


        val list = filtered.keys.toList()
        val currentIndex = list.indexOf(uuid)

        var increment = currentIndex + direction.value
        if(increment >= list.size) increment = 0
        if(increment < 0) increment = list.size - 1

        Log.d("Settings", "Move: $currentIndex $increment $direction")

        return list[increment]
    }

    override suspend fun saveCurrentUUID() {
        Log.d("Settings", "Attempting save typography.")
        repository.saveTypography(_currentUUID.value)
    }

}
