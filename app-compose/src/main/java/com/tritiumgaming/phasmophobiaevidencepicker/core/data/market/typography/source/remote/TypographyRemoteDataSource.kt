package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.network.StoreThemesApi
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.TypographyDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TypographyRemoteDataSource : TypographyDataSource<List<NetworkTypographyEntity>> {

    override suspend fun fetchAll(): List<NetworkTypographyEntity> =
        withContext(Dispatchers.IO) {
            StoreThemesApi().fetchAllTypographies()
        }

}