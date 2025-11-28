package com.tritiumgaming.shared.data.market.typography.usecase

import com.tritiumgaming.shared.data.account.model.toAccountMarketTypography
import com.tritiumgaming.shared.data.market.typography.model.toAccountMarketTypography

class FindNextAvailableTypographyUseCase(
    private val marketRepository: com.tritiumgaming.shared.data.market.typography.repository.MarketTypographyRepository,
    private val accountRepository: com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
) {
    suspend operator fun invoke(
        currentUUID: String,
        direction: com.tritiumgaming.shared.data.market.model.IncrementDirection
    ): String {

        val marketTypographies: List<com.tritiumgaming.shared.data.account.model.AccountMarketTypography> =
            marketRepository.get()
                .getOrDefault(emptyList()).toAccountMarketTypography()

        val accountTypographies: List<com.tritiumgaming.shared.data.account.model.AccountMarketTypography> =
            accountRepository.fetchUnlockedTypographies()
                .getOrDefault(emptyList()).toAccountMarketTypography()

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

        val filteredMergedMarketAccountTypographies =
            mergedMarketAccountTypographies.filter { it.isUnlocked }

        val uuidsFiltered = filteredMergedMarketAccountTypographies.map { it.uuid }
        val currentIndex = uuidsFiltered.indexOfFirst{ it == currentUUID }

        var increment = currentIndex + direction.value
        if(increment >= uuidsFiltered.size) increment = 0
        if(increment < 0) increment = uuidsFiltered.size - 1

        return uuidsFiltered[increment]
    }

}