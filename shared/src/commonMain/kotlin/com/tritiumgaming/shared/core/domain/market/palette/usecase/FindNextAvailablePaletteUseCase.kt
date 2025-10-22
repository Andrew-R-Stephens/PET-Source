package com.tritiumgaming.shared.core.domain.market.palette.usecase

import com.tritiumgaming.shared.core.domain.market.model.IncrementDirection
import com.tritiumgaming.shared.core.domain.market.palette.model.toAccountMarketPalette
import com.tritiumgaming.shared.core.domain.market.palette.repository.MarketPaletteRepository
import com.tritiumgaming.shared.core.domain.user.model.AccountMarketPalette
import com.tritiumgaming.shared.core.domain.user.model.toAccountMarketPalette
import com.tritiumgaming.shared.core.domain.user.repository.FirestoreAccountRepository

class FindNextAvailablePaletteUseCase(
    private val marketRepository: MarketPaletteRepository,
    private val accountRepository: FirestoreAccountRepository
) {
    suspend operator fun invoke(
        currentUUID: String,
        direction: IncrementDirection
    ): String {

        val marketPalettes: List<AccountMarketPalette> =
            marketRepository.get()
                .getOrDefault(emptyList()).toAccountMarketPalette()

        val accountPalettes: List<AccountMarketPalette> =
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

        return uuidsFiltered[increment]
    }

}