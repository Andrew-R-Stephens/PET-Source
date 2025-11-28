package com.tritiumgaming.shared.data.market.palette.usecase

import com.tritiumgaming.shared.data.account.model.toAccountMarketPalette
import com.tritiumgaming.shared.data.market.palette.model.toAccountMarketPalette

class FindNextAvailablePaletteUseCase(
    private val marketRepository: com.tritiumgaming.shared.data.market.palette.repository.MarketPaletteRepository,
    private val accountRepository: com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
) {
    suspend operator fun invoke(
        currentUUID: String,
        direction: com.tritiumgaming.shared.data.market.model.IncrementDirection
    ): Result<String> {

        val marketPalettes: List<com.tritiumgaming.shared.data.account.model.AccountMarketPalette> =
            marketRepository.get()
                .getOrDefault(emptyList()).toAccountMarketPalette()

        val accountPalettes: List<com.tritiumgaming.shared.data.account.model.AccountMarketPalette> =
            accountRepository.fetchUnlockedPalettes()
                .getOrDefault(emptyList()).toAccountMarketPalette()

        val mergedMarketAccountPalettes =
            accountPalettes.fold(marketPalettes) { marketPs, accountP ->
                marketPs.map { marketP ->
                    if (accountP.uuid == marketP.uuid) {
                        marketP.copy(
                            uuid = accountP.uuid,
                            unlocked = accountP.unlocked,
                            name = marketP.name,
                            group = marketP.group,
                            buyCredits = marketP.buyCredits,
                            priority = marketP.priority,
                            palette = marketP.palette
                        )
                    } else marketP
                }
            }

        val filteredMergedMarketAccountPalettes =
            mergedMarketAccountPalettes.filter { it.isUnlocked }

        val uuidsFiltered = filteredMergedMarketAccountPalettes.map { it.uuid }
        val currentIndex = uuidsFiltered.indexOfFirst{ it == currentUUID }

        var increment = currentIndex + direction.value
        if(increment >= uuidsFiltered.size) increment = 0
        if(increment < 0) increment = uuidsFiltered.size - 1

        return Result.success(uuidsFiltered[increment])
    }

}