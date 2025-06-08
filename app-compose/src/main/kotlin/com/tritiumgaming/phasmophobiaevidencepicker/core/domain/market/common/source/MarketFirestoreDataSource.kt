package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.common.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.MarketTypographyFirestoreDataSource.TypographyQueryOptions

/**
 * @param D - DTO
 * @param Q - Query Options
 */
interface MarketFirestoreDataSource<D, Q> {
    suspend fun fetch(queryOptions: Q): Result<List<D>>
}