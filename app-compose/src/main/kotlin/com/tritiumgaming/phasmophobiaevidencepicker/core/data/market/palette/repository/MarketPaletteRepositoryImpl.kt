package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.repository

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto.MarketPaletteDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.mapper.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.mapper.toLocal
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local.MarketPaletteLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.MarketPaletteFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.MarketPaletteFirestoreDataSource.PaletteQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.MarketPaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MarketPaletteRepositoryImpl(
    private val firestoreDataSource: MarketPaletteFirestoreDataSource,
    private val localDataSource: MarketPaletteLocalDataSource,
    private val dataStoreSource: PaletteDatastore,
    coroutineDispatcher: CoroutineDispatcher
): MarketPaletteRepository {

    private var cache: List<MarketPaletteDto> = emptyList()

    override fun getLocal(): Result<List<MarketPaletteDto>> {
        Log.d("Palette", "Getting local palettes")

        val result = localDataSource.getPalettes()
        result.exceptionOrNull()?.let { e ->
            Log.d("Palette", "Error getting local palettes: $e") }
        val list: List<MarketPaletteDto> = result.getOrDefault(emptyMap()).toLocal()

        return Result.success(list)
    }

    override suspend fun fetchRemote(
        queryOptions: PaletteQueryOptions
    ): Result<List<MarketPaletteDto>> {
        Log.d("Palette", "Fetching remote palettes")

        val result = firestoreDataSource.fetch(queryOptions)

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
                        palette = localEntity.palette ?: ExtendedPalette()
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

    override fun initialSetupEvent() {
        dataStoreSource.initialSetupEvent()
    }

    override suspend fun initFlow(): Flow<PaletteDatastore.PalettePreferences> =
        dataStoreSource.flow

    override suspend fun saveCurrent(uuid: String) {
        dataStoreSource.savePalette(uuid)
    }

    init {
        initialSetupEvent()

        CoroutineScope(coroutineDispatcher).launch {
            synchronizeCache()
        }
    }
}