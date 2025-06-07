package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.usecase.preference

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.market.model.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.toAccountMarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.MarketPaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.toAccountMarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.MarketTypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountMarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountMarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.toAccountMarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.toAccountMarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository.FirestoreAccountRepository
import kotlin.collections.forEach

class FindNextAvailableTypographyUseCase(
    private val marketRepository: MarketTypographyRepository,
    private val accountRepository: FirestoreAccountRepository
) {
    suspend operator fun invoke(
        currentUUID: String,
        direction: IncrementDirection
    ): String {

        val marketTypographies: List<AccountMarketTypography> =
            marketRepository.getTypographies().toAccountMarketTypography()

        Log.d("Settings", "MarketTypographies:")
        marketTypographies.forEach {
            Log.d("Settings", "\t$it")
        }

        val accountTypographies: List<AccountMarketTypography> =
            (accountRepository.fetchUnlockedTypographies().getOrNull()?.toAccountMarketTypography()
                ?: listOf())

        Log.d("Settings", "AccountTypographies:")
        accountTypographies.forEach {
            Log.d("Settings", "\t$it")
        }

        val marketAccountTypographies: MutableList<AccountMarketTypography> = mutableListOf()

        accountTypographies.forEach { accountTypography ->
            val foundMarketTypography = marketTypographies.find {
                marketTypography -> marketTypography.uuid == accountTypography.uuid }

            Log.d("Settings", "Found matching typographies:\n\t$foundMarketTypography\n\t$accountTypography")

            foundMarketTypography?.let { mp ->
                mp.copy(
                    unlocked = accountTypography.unlocked,
                    priority = accountTypography.priority
                )
                marketAccountTypographies.add(mp)
            }
        }

        Log.d("Settings", "MarketAccountTypographies:")
        marketAccountTypographies.forEach {
            Log.d("Settings", "\t$it")
        }

        return currentUUID

        /*marketAccountPalettes.forEach {
            Log.d("Settings", "AccountMarketPalettes: ${it.uuid}, ${it.name}, ${it.unlocked}")
        }

        val uuidsFiltered = marketAccountPalettes.map { it.uuid }
        val currentIndex = uuidsFiltered.indexOfFirst{ it == currentUUID }

        var increment = currentIndex + direction.value
        if(increment >= uuidsFiltered.size) increment = 0
        if(increment < 0) increment = uuidsFiltered.size - 1

        Log.d("Settings", "Move: $currentIndex $increment $direction")

        return uuidsFiltered[increment]*/
    }

}