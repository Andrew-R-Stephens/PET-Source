package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper.toExternal
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper.toLocal
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local.MarketTypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.MarketTypographyFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.MarketTypographyFirestoreDataSource.TypographyQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.MarketTypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MarketTypographyRepositoryImpl(
    private val firestoreDataSource: MarketTypographyFirestoreDataSource,
    private val localDataSource: MarketTypographyLocalDataSource,
    private val dataStoreSource: MarketTypographyDatastore,
    coroutineDispatcher: CoroutineDispatcher
): MarketTypographyRepository {

    private var cache: List<MarketTypographyDto> = localDataSource.getTypographies().toLocal()

    override fun getLocalTypographies(): List<MarketTypographyDto> =
        localDataSource.getTypographies().toLocal()

    override suspend fun fetchRemoteTypographies(
        typographyQueryOptions: TypographyQueryOptions
    ): List<MarketTypographyDto> = firestoreDataSource.query(typographyQueryOptions)

    override suspend fun synchronizeTypographies() {
        val local: List<MarketTypography> = getLocalTypographies().toExternal()
        val remote: List<MarketTypography> = fetchRemoteTypographies().toExternal()

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
    }

    override fun getTypographies(): List<MarketTypography> {
        return cache.toExternal()
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

        CoroutineScope(coroutineDispatcher).launch {
            cache = getLocalTypographies()
            synchronizeTypographies()
        }
    }

}