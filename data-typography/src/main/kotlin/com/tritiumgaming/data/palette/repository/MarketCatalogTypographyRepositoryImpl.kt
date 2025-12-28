package com.tritiumgaming.data.palette.repository

import android.util.Log
import com.tritiumgaming.data.palette.dto.MarketTypographyDto
import com.tritiumgaming.data.palette.dto.toDomain
import com.tritiumgaming.data.palette.dto.toLocal
import com.tritiumgaming.data.palette.source.local.MarketTypographyLocalDataSource
import com.tritiumgaming.data.palette.source.remote.MarketTypographyFirestoreDataSource
import com.tritiumgaming.shared.data.market.typography.model.MarketTypography
import com.tritiumgaming.shared.data.market.typography.model.TypographyQueryOptions
import com.tritiumgaming.shared.data.market.typography.repository.MarketCatalogTypographyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MarketCatalogTypographyRepositoryImpl(
    private val firestoreDataSource: MarketTypographyFirestoreDataSource,
    private val localDataSource: MarketTypographyLocalDataSource,
    coroutineDispatcher: CoroutineDispatcher
): MarketCatalogTypographyRepository {

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

    override fun get(): Result<List<MarketTypography>> = Result.success(cache.toDomain())

    init {
        CoroutineScope(coroutineDispatcher).launch {
            synchronizeCache()
        }
    }
}