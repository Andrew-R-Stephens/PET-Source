package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local.TypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.TypographyRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.TypographyEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.toExternal
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyRepository

class TypographyRepositoryImpl(
    private val networkDataSource: TypographyRemoteDataSource,
    private val localDataSource: TypographyLocalDataSource
): TypographyRepository {

    override suspend fun getLocalTypographies(): List<TypographyEntity> =
        localDataSource.fetchAll().toExternal()


    override suspend fun getRemoteTypographies(): List<TypographyEntity> =
        networkDataSource.fetchAll().toExternal()

}