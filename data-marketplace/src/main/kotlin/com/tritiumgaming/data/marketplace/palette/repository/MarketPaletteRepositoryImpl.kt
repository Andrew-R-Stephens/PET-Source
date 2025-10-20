package com.tritiumgaming.data.marketplace.palette.repository

import android.util.Log
import com.tritiumgaming.data.marketplace.palette.dto.MarketPaletteDto
import com.tritiumgaming.data.marketplace.palette.dto.toDomain
import com.tritiumgaming.data.marketplace.palette.dto.toLocal
import com.tritiumgaming.data.marketplace.palette.source.local.MarketPaletteLocalDataSource
import com.tritiumgaming.data.marketplace.palette.source.remote.MarketPaletteFirestoreDataSource
import com.tritiumgaming.shared.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.shared.core.domain.market.palette.model.PaletteQueryOptions
import com.tritiumgaming.shared.core.domain.market.palette.repository.MarketPaletteRepository
import com.tritiumgaming.shared.core.domain.market.palette.source.PaletteDatastore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MarketPaletteRepositoryImpl(
    private val firestoreDataSource: MarketPaletteFirestoreDataSource,
    private val localDataSource: MarketPaletteLocalDataSource,
    private val dataStoreSource: PaletteDatastore,
    coroutineDispatcher: CoroutineDispatcher
): MarketPaletteRepository {

    override fun initializeDatastoreLiveData() = dataStoreSource.initializeDatastoreLiveData()

    override fun initDatastoreFlow(
        onUpdate: (PaletteDatastore.PalettePreferences) -> Unit
    ) = dataStoreSource.initDatastoreFlow()

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

    override fun get(): Result<List<MarketPalette>> {
        Log.d("Palette", "Getting ${cache.size} cached palettes:")
        cache.forEach {
            Log.d("Palette", "\t$it")
        }

        return Result.success(cache.toDomain())
    }

    override suspend fun saveCurrent(uuid: String) {
        dataStoreSource.savePalette(uuid)
    }

    init {
        initializeDatastoreLiveData()

        CoroutineScope(coroutineDispatcher).launch {
            synchronizeCache()
        }
    }
}