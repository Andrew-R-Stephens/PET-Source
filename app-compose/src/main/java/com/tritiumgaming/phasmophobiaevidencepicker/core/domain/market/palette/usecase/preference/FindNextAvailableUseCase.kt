package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.market.model.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.MarketPaletteRepository

class FindNextAvailablePaletteUseCase(
    private val repository: MarketPaletteRepository
) {

    operator fun invoke(
        currentUUID: String,
        direction: IncrementDirection
    ): String {

        val palettes = repository.getPalettes()

        val filtered = palettes
            .filter { palette ->
                palette.palette != null && palette.isUnlocked == true
            }

        val uuidsFiltered = filtered.map { it.uuid }
        val currentIndex = uuidsFiltered.indexOfFirst{ it == currentUUID }

        var increment = currentIndex + direction.value
        if(increment >= uuidsFiltered.size) increment = 0
        if(increment < 0) increment = uuidsFiltered.size - 1

        Log.d("Settings", "Move: $currentIndex $increment $direction")

        return uuidsFiltered[increment]
    }

}