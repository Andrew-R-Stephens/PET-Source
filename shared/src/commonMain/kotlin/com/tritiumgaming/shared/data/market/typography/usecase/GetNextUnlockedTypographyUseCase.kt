package com.tritiumgaming.shared.data.market.typography.usecase

import com.tritiumgaming.shared.data.account.model.AccountMarketTypography
import com.tritiumgaming.shared.data.market.model.IncrementDirection

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