package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.mapper.toPair
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper.toExternal
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper.toPair
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.market.model.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.TypographyDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.FetchTypographyUsecase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.FindNextAvailableTypographyUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.GetTypographyByUUIDUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalDefaultPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalDefaultTypography
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TypographyManager(
    private val repository: TypographyRepository,
    private val datastore: TypographyDatastore,
    private val fetchTypographiesUseCase: FetchTypographyUsecase,
    private val getTypographyByUUIDUseCase: GetTypographyByUUIDUseCase,
    private val findNextAvailableTypographyUseCase: FindNextAvailableTypographyUseCase
) : AThemeManager(
    defaultUUID = LocalDefaultTypography.uuid
) {

    private val _typographies = MutableStateFlow(mapOf<String, MarketTypography>())
    var typographies = _typographies.asStateFlow()

    suspend fun fetchTypographies() {
        val mergedModels = fetchTypographiesUseCase.invoke()

        _typographies.update { mergedModels.toPair() }
    }

    fun getTypographyByUUID(uuid: String): ExtendedTypography =
        getTypographyByUUIDUseCase.invoke(typographies.value, uuid, LocalDefaultTypography.typography)

    fun findNextAvailable(
        currentUUID: StateFlow<String>,
        direction: IncrementDirection
    ): String = findNextAvailableTypographyUseCase.invoke(typographies.value, currentUUID.value, direction)

    fun findNextAvailable(
        direction: IncrementDirection
    ): String {
        return findNextAvailable(currentUUID, direction)
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
