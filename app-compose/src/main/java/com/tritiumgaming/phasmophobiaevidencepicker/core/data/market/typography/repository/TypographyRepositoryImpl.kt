package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper.toLocal
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local.TypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.TypographyRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyRepository

class TypographyRepositoryImpl(
    private val networkDataSource: TypographyRemoteDataSource,
    private val localDataSource: TypographyLocalDataSource
): TypographyRepository {

    override suspend fun getRemoteTypographies(): List<MarketTypographyDto> =
        networkDataSource.fetchAll()

    override suspend fun getLocalTypographies(): List<MarketTypographyDto> =
        localDataSource.fetchAll().toLocal()

}