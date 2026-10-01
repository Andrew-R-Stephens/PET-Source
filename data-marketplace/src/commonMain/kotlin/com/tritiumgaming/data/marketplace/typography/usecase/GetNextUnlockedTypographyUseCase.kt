package com.tritiumgaming.data.marketplace.typography.usecase

import com.tritiumgaming.data.marketplace.account.model.AccountMarketTypography
import com.tritiumgaming.data.marketplace.model.IncrementDirection

class GetNextUnlockedTypographyUseCase {
    operator fun invoke(
        typographies: List<AccountMarketTypography>,
        currentUUID: String,
        direction: IncrementDirection
    ): Result<String> {

        val uuidsFiltered = typographies.map { it.uuid }
        val currentIndex = uuidsFiltered.indexOfFirst{ it == currentUUID }

        var increment = currentIndex + direction.value
        if(increment >= uuidsFiltered.size) increment = 0
        if(increment < 0) increment = uuidsFiltered.size - 1

        println("getNextUnlockedTypographyUseCase returning uuids")
        return Result.success(uuidsFiltered[increment])
    }

}