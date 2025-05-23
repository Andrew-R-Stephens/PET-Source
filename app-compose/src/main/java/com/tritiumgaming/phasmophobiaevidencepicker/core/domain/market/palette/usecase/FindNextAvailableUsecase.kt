package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.market.model.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.MarketPalette

class FindNextAvailablePaletteUseCase {

    operator fun invoke(
        palettes: Map<String, MarketPalette>,
        currentUUID: String,
        direction: IncrementDirection
    ): String {

        Log.d("Settings", "$currentUUID ${palettes.size}")
        if(palettes.isEmpty()) return ""

        val filtered = palettes
            .filter {
                it.value.palette != null && it.value.isUnlocked == true
            }

        Log.d("Settings", "Filtered: ${filtered.size}")
        if(filtered.isEmpty()) return ""

        val list = filtered.keys.toList()
        val currentIndex = list.indexOf(currentUUID)

        var increment = currentIndex + direction.value
        if(increment >= list.size) increment = 0
        if(increment < 0) increment = list.size - 1

        Log.d("Settings", "Move: $currentIndex $increment $direction")

        return list[increment]
    }

}