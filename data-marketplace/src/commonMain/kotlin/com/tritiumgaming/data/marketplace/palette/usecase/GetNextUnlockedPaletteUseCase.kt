package com.tritiumgaming.data.marketplace.palette.usecase

import com.tritiumgaming.data.marketplace.account.model.AccountMarketPalette
import com.tritiumgaming.data.marketplace.model.IncrementDirection

class GetNextUnlockedPaletteUseCase {
    operator fun invoke(
        palettes: List<AccountMarketPalette>,
        currentUUID: String,
        direction: IncrementDirection
    ): Result<String> {

        val uuidsFiltered = palettes.filter { it.unlocked }.map { it.uuid }
        val currentIndex = uuidsFiltered.indexOfFirst{ it == currentUUID }

        var increment = currentIndex + direction.value
        if(increment >= uuidsFiltered.size) increment = 0
        if(increment < 0) increment = uuidsFiltered.size - 1

        println("getNextUnlockedPaletteUseCase returning uuids")
        return Result.success(uuidsFiltered[increment])
    }

}