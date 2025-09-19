package com.tritiumgaming.shared.core.domain.market.palette.palette.usecase.preference

import com.tritiumgaming.shared.core.domain.market.palette.repository.MarketPaletteRepository

class SaveCurrentPaletteUseCase(
    private val repository: MarketPaletteRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(uuid: String) = repository.saveCurrent(uuid)

}