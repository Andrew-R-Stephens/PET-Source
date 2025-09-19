package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper.toLocal
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local.MarketTypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.MarketTypographyFirestoreDataSource
import com.tritiumgaming.shared.core.domain.market.typography.model.MarketTypography
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyQueryOptions
import com.tritiumgaming.shared.core.domain.market.typography.repository.MarketTypographyRepository
import com.tritiumgaming.shared.core.domain.market.typography.source.MarketTypographyDatastore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MarketTypographyRepositoryImpl(
    private val firestoreDataSource: MarketTypographyFirestoreDataSource,
    private val localDataSource: MarketTypographyLocalDataSource,
    private val dataStoreSource: MarketTypographyDatastore,
    coroutineDispatcher: CoroutineDispatcher
): MarketTypographyRepository {

    override fun initializeDatastoreLiveData() = dataStoreSource.initializeDatastoreLiveData()

    override suspend fun initDatastoreFlow(
        onUpdate: (MarketTypographyDatastore.TypographyPreferences) -> Unit
    ) = dataStoreSource.initDatastoreFlow(onUpdate)

    private var cache: List<MarketTypographyDto> = emptyList()

    fun getLocal(): Result<List<MarketTypographyDto>> {
        Log.d("Typography", "Getting local typographies")

        val result = localDataSource.get()
        result.exceptionOrNull()?.let { e ->
            Log.d("Typography", "Error getting local typographies: $e") }
        val list: List<MarketTypographyDto> = result.getOrDefault(emptyMap()).toLocal()

        return Result.success(list)
    }

    suspend fun fetchRemote(
        queryOptions: TypographyQueryOptions = TypographyQueryOptions()
    ): Result<List<MarketTypographyDto>> {
        Log.d("Typography", "Fetching remote typographies")

        val result = firestoreDataSource.fetch(queryOptions)

        return result
    }

    override suspend fun synchronizeCache(): Result<List<MarketTypography>> {
        Log.d("Typography", "Fetched typographies")

        val localResult = getLocal()
        localResult.exceptionOrNull()?.let { e ->
            Log.d("Typography", "Error getting local typographies: $e") }
        val local = localResult.getOrDefault(emptyList())

        val remoteResult = fetchRemote()
        remoteResult.exceptionOrNull()?.let { e ->
            Log.d("Typography", "Error getting remote typographies: $e") }
        val remote = remoteResult.getOrDefault(emptyList())

        val mergedModels = remote.fold(local) { localList, remoteEntity ->
            localList.map { localEntity ->
                if (localEntity.uuid == remoteEntity.uuid)
                    localEntity.copy(
                        uuid = localEntity.uuid,
                        name = remoteEntity.name,
                        group = remoteEntity.group,
                        buyCredits = remoteEntity.buyCredits,
                        unlocked = remoteEntity.unlocked,
                        priority = remoteEntity.priority,
                        typography = remoteEntity.typography
                    )
                else localEntity
            }
        }

        Log.d("Typography", "Fetched ${mergedModels.size} typographies")
        mergedModels.forEach {
            Log.d("Typography", "Fetched $it")
        }

        cache = mergedModels

        return Result.success(cache.toDomain())
    }

    override fun get(): Result<List<MarketTypography>> {
        Log.d("Typography", "Getting ${cache.size} cached typographies:")
        cache.forEach {
            Log.d("Palette", "\t$it")
        }

        return Result.success(cache.toDomain())
    }

    override suspend fun saveCurrent(uuid: String) {
        dataStoreSource.saveTypography(uuid)
    }

    init {
        initializeDatastoreLiveData()

        CoroutineScope(coroutineDispatcher).launch {
            synchronizeCache()
        }
    }
}