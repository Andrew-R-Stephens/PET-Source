package com.tritiumgaming.data.marketplace.typography.usecase

import com.tritiumgaming.data.account.repository.FirestoreAccountRepository
import com.tritiumgaming.data.marketplace.account.model.AccountMarketTypography
import com.tritiumgaming.data.marketplace.account.model.toAccountMarketTypography
import com.tritiumgaming.data.marketplace.typography.model.toAccountMarketTypography
import com.tritiumgaming.data.marketplace.typography.repository.MarketCatalogTypographyRepository

class FetchUnlockedTypographiesUseCase(
    private val marketRepository: MarketCatalogTypographyRepository,
    private val accountRepository: FirestoreAccountRepository
) {
    suspend operator fun invoke(): Result<List<AccountMarketTypography>> {

        println("getNextUnlockedPaletteUseCase getting Market Palettes")
        val marketTypographies: List<AccountMarketTypography> =
            marketRepository.get()
                .getOrDefault(emptyList()).toAccountMarketTypography()

        println("getNextUnlockedPaletteUseCase getting Account Palettes")
        val accountTypographies: List<AccountMarketTypography> =
            accountRepository.fetchUnlockedTypographies()
                .getOrDefault(emptyList()).toAccountMarketTypography()

        println("getNextUnlockedPaletteUseCase merging Market Palettes")
        val mergedMarketAccountTypographies =
            accountTypographies.fold(marketTypographies) { marketTs, accountT ->
                marketTs.map { marketT ->
                    if (accountT.uuid == marketT.uuid) {
                        marketT.copy(
                            uuid = accountT.uuid,
                            unlocked = accountT.unlocked,
                            name = marketT.name,
                            group = marketT.group,
                            buyCredits = marketT.buyCredits,
                            priority = marketT.priority,
                            typography = marketT.typography
                        )
                    } else marketT
                }
            }

        println("getNextUnlockedPaletteUseCase filtering for unlocked Market Palettes")
        val filteredMergedMarketAccountTypographies =
            mergedMarketAccountTypographies.filter { it.isUnlocked }

        return Result.success(filteredMergedMarketAccountTypographies)
    }

}