package com.tritiumgaming.shared.data.market.bundle.usecase

import com.tritiumgaming.shared.data.market.bundle.model.MarketBundle
import com.tritiumgaming.shared.data.market.bundle.repository.MarketCatalogBundleRepository

class GetMarketCatalogBundlesUseCase(
    private val repository: MarketCatalogBundleRepository
) {

    operator fun invoke(): Result<List<MarketBundle>> {
        return try {
            Result.success(repository.get().getOrThrow())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("Failed to obtain MarketBundle Catalog cache"))
        }
    }

}
