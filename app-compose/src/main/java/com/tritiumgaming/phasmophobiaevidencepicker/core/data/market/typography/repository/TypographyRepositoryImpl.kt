package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper.toExternal
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local.TypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.TypographyRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyRepository

class TypographyRepositoryImpl(
    private val networkDataSource: TypographyRemoteDataSource,
    private val localDataSource: TypographyLocalDataSource
): TypographyRepository {

    override suspend fun getLocalTypographies(): List<MarketTypography> =
        localDataSource.fetchAll().toExternal()


    override suspend fun getRemoteTypographies(): List<MarketTypography> =
        networkDataSource.fetchAll().toExternal()

}