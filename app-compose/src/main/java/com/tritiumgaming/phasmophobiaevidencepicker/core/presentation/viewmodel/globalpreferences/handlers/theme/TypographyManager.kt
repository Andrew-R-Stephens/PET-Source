package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.theme

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.market.core.ThemeRepository.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.market.typography.TypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.typography.TypographyDatastore
import kotlinx.coroutines.flow.update

class TypographyManager(
    private val repository: TypographyRepository,
    private val datastore: TypographyDatastore
) : AThemeManager(
    defaultUUID = repository.defaultTypography.uuid
) {

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

    fun findNextAvailable(
        direction: IncrementDirection
    ): String {
        return repository.findNextAvailable(currentUUID, direction)
    }

    override suspend fun saveCurrentUUID() {
        Log.d("Settings", "Attempting save typography.")
        datastore.saveTypography(currentUUID.value)
    }

}
