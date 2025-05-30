package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.market.model.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.MarketTypographyRepository

class FindNextAvailableTypographyUseCase(
    repository: MarketTypographyRepository
){

    operator fun invoke(
        typographies: Map<String, MarketTypography>,
        currentUUID: String,
        direction: IncrementDirection
    ): String {

        Log.d("Settings", "$currentUUID ${typographies.size}")
        if(typographies.isEmpty()) return ""

        val filtered = typographies
            .filter {
                it.value.typography != null && it.value.isUnlocked == true
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