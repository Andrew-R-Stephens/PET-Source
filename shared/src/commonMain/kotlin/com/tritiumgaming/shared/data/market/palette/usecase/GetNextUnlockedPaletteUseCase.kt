package com.tritiumgaming.shared.data.market.palette.usecase

import com.tritiumgaming.shared.data.account.model.AccountMarketPalette
import com.tritiumgaming.shared.data.account.model.toAccountMarketPalette
import com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
import com.tritiumgaming.shared.data.market.model.IncrementDirection
import com.tritiumgaming.shared.data.market.palette.model.toAccountMarketPalette
import com.tritiumgaming.shared.data.market.palette.repository.MarketCatalogPaletteRepository

class GetNextUnlockedPaletteUseCase {
    operator fun invoke(
        palettes: List<AccountMarketPalette>,
        currentUUID: String,
        direction: IncrementDirection
    ): Result<String> {

        val uuidsFiltered = palettes.map { it.uuid }
        val currentIndex = uuidsFiltered.indexOfFirst{ it == currentUUID }

        var increment = currentIndex + direction.value
        if(increment >= uuidsFiltered.size) increment = 0
        if(increment < 0) increment = uuidsFiltered.size - 1

        println("getNextUnlockedPaletteUseCase returning uuids")
        return Result.success(uuidsFiltered[increment])
    }

}