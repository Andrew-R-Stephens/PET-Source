package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper.toLocal
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local.MarketTypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.MarketTypographyFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.MarketTypographyFirestoreDataSource.TypographyQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.MarketTypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class MarketTypographyRepositoryImpl(
    private val firestoreDataSource: MarketTypographyFirestoreDataSource,
    private val localDataSource: MarketTypographyLocalDataSource,
    private val dataStoreSource: MarketTypographyDatastore,
    coroutineDispatcher: CoroutineDispatcher
): MarketTypographyRepository {

    private var cache: List<MarketTypographyDto> = emptyList()

    override fun getLocalTypographies(): List<MarketTypographyDto> =
        localDataSource.getTypographies().toLocal()

    override suspend fun fetchRemoteTypographies(
        typographyQueryOptions: TypographyQueryOptions
    ): List<MarketTypographyDto> {
        val result = firestoreDataSource.fetch(typographyQueryOptions)

        return result.getOrNull() ?: emptyList()
    }

    override suspend fun synchronizeTypographies(): List<MarketTypography> {
        Log.d("Typography", "Fetched typographies")

        val local: List<MarketTypographyDto> = getLocalTypographies()
        val remote: List<MarketTypographyDto> = fetchRemoteTypographies()

        val mergedModels = remote.fold(local) { localList, remoteEntity ->
            localList.map { localEntity ->
                if (localEntity.uuid == remoteEntity.uuid) localEntity.copy(
                    uuid = localEntity.uuid,
                    name = remoteEntity.name,
                    group = remoteEntity.group,
                    buyCredits = remoteEntity.buyCredits,
                    priority = remoteEntity.priority,
                    unlocked = remoteEntity.unlocked,
                    typography = remoteEntity.typography ?: ExtendedTypography()
                )
                else localEntity
            }
        }

        Log.d("Typography", "Fetched ${mergedModels.size} typographies")
        mergedModels.forEach {
            Log.d("Typography", "Fetched $it")
        }

        cache = mergedModels

        return cache.toDomain()
    }

    override fun getTypographies(): List<MarketTypography> {
        return cache.toDomain()
    }

    override fun initialSetupEvent() {
        dataStoreSource.initialSetupEvent()
    }

    override suspend fun initFlow(): Flow<MarketTypographyDatastore.TypographyPreferences> =
        dataStoreSource.flow

    override suspend fun saveCurrentTypography(uuid: String) {
        dataStoreSource.saveTypography(uuid)
    }

    init {
        initialSetupEvent()

        /*CoroutineScope(coroutineDispatcher).launch {
            synchronizeTypographies()
        }*/
    }

}