package com.tritiumgaming.shared.data.market.palette.usecase

import com.tritiumgaming.shared.data.account.model.AccountMarketPalette
import com.tritiumgaming.shared.data.account.model.toAccountMarketPalette
import com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
import com.tritiumgaming.shared.data.market.palette.model.toAccountMarketPalette
import com.tritiumgaming.shared.data.market.palette.repository.MarketCatalogPaletteRepository

class FetchUnlockedPalettesUseCase(
    private val marketRepository: MarketCatalogPaletteRepository,
    private val accountRepository: FirestoreAccountRepository
) {
    suspend operator fun invoke(): Result<List<AccountMarketPalette>> {

        println("getNextUnlockedPaletteUseCase getting Market Palettes")
        val marketPalettes: List<AccountMarketPalette> =
            marketRepository.get()
                .getOrDefault(emptyList()).toAccountMarketPalette()

        println("getNextUnlockedPaletteUseCase getting Account Palettes")
        val accountPalettes: List<AccountMarketPalette> =
            accountRepository.fetchUnlockedPalettes()
                .getOrDefault(emptyList()).toAccountMarketPalette()

        println("getNextUnlockedPaletteUseCase merging Market Palettes")
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

        println("getNextUnlockedPaletteUseCase filtering for unlocked Market Palettes")
        val filteredMergedMarketAccountPalettes =
            mergedMarketAccountPalettes.filter { it.isUnlocked }

        return Result.success(filteredMergedMarketAccountPalettes)
    }

}