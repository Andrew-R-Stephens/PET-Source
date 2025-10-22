package com.tritiumgaming.shared.core.domain.market.typography.usecase

import com.tritiumgaming.shared.core.domain.market.model.IncrementDirection
import com.tritiumgaming.shared.core.domain.market.typography.model.toAccountMarketTypography
import com.tritiumgaming.shared.core.domain.market.typography.repository.MarketTypographyRepository
import com.tritiumgaming.shared.core.domain.user.model.AccountMarketTypography
import com.tritiumgaming.shared.core.domain.user.model.toAccountMarketTypography
import com.tritiumgaming.shared.core.domain.user.repository.FirestoreAccountRepository

class FindNextAvailableTypographyUseCase(
    private val marketRepository: MarketTypographyRepository,
    private val accountRepository: FirestoreAccountRepository
) {
    suspend operator fun invoke(
        currentUUID: String,
        direction: IncrementDirection
    ): String {

        val marketTypographies: List<AccountMarketTypography> =
            marketRepository.get()
                .getOrDefault(emptyList()).toAccountMarketTypography()

        val accountTypographies: List<AccountMarketTypography> =
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