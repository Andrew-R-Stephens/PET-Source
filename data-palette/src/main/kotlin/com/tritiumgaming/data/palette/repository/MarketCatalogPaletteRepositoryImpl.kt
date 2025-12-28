package com.tritiumgaming.data.palette.repository

import android.util.Log
import com.tritiumgaming.data.palette.dto.MarketPaletteDto
import com.tritiumgaming.data.palette.dto.toDomain
import com.tritiumgaming.data.palette.dto.toLocal
import com.tritiumgaming.data.palette.source.local.MarketPaletteLocalDataSource
import com.tritiumgaming.data.palette.source.remote.MarketPaletteFirestoreDataSource
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette
import com.tritiumgaming.shared.data.market.palette.model.PaletteQueryOptions
import com.tritiumgaming.shared.data.market.palette.repository.MarketCatalogPaletteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MarketCatalogPaletteRepositoryImpl(
    private val firestoreDataSource: MarketPaletteFirestoreDataSource,
    private val localDataSource: MarketPaletteLocalDataSource,
    coroutineDispatcher: CoroutineDispatcher
): MarketCatalogPaletteRepository {

    private var cache: List<MarketPaletteDto> = emptyList()

    fun getLocal(): Result<List<MarketPaletteDto>> {
        Log.d("Palette", "Getting local palettes")

        val result = localDataSource.getPalettes()
        result.exceptionOrNull()?.let { e ->
            Log.d("Palette", "Error getting local palettes: $e") }
        val list: List<MarketPaletteDto> = result.getOrDefault(emptyMap()).toLocal()

        return Result.success(list)
    }

    suspend fun fetchRemote(
        queryOptions: PaletteQueryOptions? = null
    ): Result<List<MarketPaletteDto>> {
        Log.d("Palette", "Fetching remote palettes")

        val result = firestoreDataSource.fetch(queryOptions ?: PaletteQueryOptions())

        return result
    }

    override suspend fun synchronizeCache(): Result<List<MarketPalette>> {
        Log.d("Palette", "Synchronizing palettes")

        val localResult = getLocal()
        localResult.exceptionOrNull()?.let { e ->
            Log.d("Palette", "Error getting local palettes: $e") }
        val local = localResult.getOrDefault(emptyList())

        val remoteResult = fetchRemote()
        remoteResult.exceptionOrNull()?.let { e ->
            Log.d("Palette", "Error getting remote palettes: $e") }
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
                        palette = localEntity.palette
                    )
                else localEntity
            }
        }

        Log.d("Palette", "Fetched ${mergedModels.size} palettes")
        mergedModels.forEach {
            Log.d("Palette", "Fetched $it")
        }

        cache = mergedModels

        return Result.success(cache.toDomain())
    }

    override fun get(): Result<List<MarketPalette>> = Result.success(cache.toDomain())

    init {
        CoroutineScope(coroutineDispatcher).launch {
            synchronizeCache()
        }
    }
}