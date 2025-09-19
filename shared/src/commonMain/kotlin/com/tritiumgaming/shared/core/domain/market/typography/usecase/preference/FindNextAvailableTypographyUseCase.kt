package com.tritiumgaming.shared.core.domain.market.typography.usecase.preference

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

        /*Log.d("Settings", "MarketTypographies:")
        marketTypographies.forEach {
            Log.d("Settings", "\t$it")
        }*/

        val accountTypographies: List<AccountMarketTypography> =
            accountRepository.fetchUnlockedTypographies()
                .getOrDefault(emptyList()).toAccountMarketTypography()

        /*Log.d("Settings", "AccountTypographies:")
        accountTypographies.forEach {
            Log.d("Settings", "\t$it")
        }*/

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

        /*Log.d("Settings", "MarketAccountTypographies:")
        mergedMarketAccountTypographies.forEach {
            Log.d("Settings", "\t$it")
        }*/

        val filteredMergedMarketAccountTypographies =
            mergedMarketAccountTypographies.filter { it.isUnlocked }
        /*Log.d("Settings", "Filtered MarketAccountTypographies:")
        filteredMergedMarketAccountTypographies.forEach {
            Log.d("Settings", "\t$it")
        }*/

        val uuidsFiltered = filteredMergedMarketAccountTypographies.map { it.uuid }
        val currentIndex = uuidsFiltered.indexOfFirst{ it == currentUUID }

        var increment = currentIndex + direction.value
        if(increment >= uuidsFiltered.size) increment = 0
        if(increment < 0) increment = uuidsFiltered.size - 1

        //Log.d("Settings", "Move: $currentIndex $increment $direction")

        return uuidsFiltered[increment]
    }

}