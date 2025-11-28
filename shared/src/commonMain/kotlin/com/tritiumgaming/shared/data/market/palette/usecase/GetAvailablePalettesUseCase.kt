package com.tritiumgaming.shared.data.market.palette.usecase

class GetAvailablePalettesUseCase(
    private val repository: com.tritiumgaming.shared.data.market.palette.repository.MarketPaletteRepository
) {

    suspend operator fun invoke(): List<com.tritiumgaming.shared.data.market.palette.model.MarketPalette> {
        return repository.synchronizeCache().getOrDefault(emptyList())
    }

}