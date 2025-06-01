package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.repository

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.dto.MarketBillableDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.source.remote.MarketBillableFirestoreDataSource.BillableQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.remote.MarketFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.remote.MarketMerchandiseFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto.MarketPaletteDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.mapper.toExternal
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
    private val marketFirestoreDataSource: MarketFirestoreDataSource,
    private val merchandiseFirestoreDataSource: MarketMerchandiseFirestoreDataSource,
    private val remoteDataSource: MarketPaletteFirestoreDataSource,
    private val localDataSource: MarketPaletteLocalDataSource,
    private val dataStoreSource: PaletteDatastore,
    coroutineDispatcher: CoroutineDispatcher
): MarketPaletteRepository {

    private var cache: List<MarketPaletteDto> = localDataSource.getPalettes().toLocal()

    override fun getLocalPalettes(): List<MarketPaletteDto> =
        localDataSource.getPalettes().toLocal()

    override suspend fun fetchRemotePalettes(
        paletteQueryOptions: PaletteQueryOptions
    ): List<MarketPaletteDto> {
        val storeCollectionRef = marketFirestoreDataSource.storeCollectionRef
        val merchandiseDocument = merchandiseFirestoreDataSource
            .getMerchandiseDocument(storeCollectionRef)

        return remoteDataSource.query(merchandiseDocument)
    }

    override suspend fun synchronizePalettes() {
        val local: List<MarketPalette> = getLocalPalettes().toExternal()
        val remote: List<MarketPalette> = fetchRemotePalettes().toExternal()

        val mergedModels = remote.fold(local) { localList, remoteEntity ->
            localList.map { localEntity ->
                if (localEntity.uuid == remoteEntity.uuid) localEntity.copy(
                    uuid = localEntity.uuid,
                    name = remoteEntity.name,
                    group = remoteEntity.group,
                    buyCredits = remoteEntity.buyCredits,
                    priority = remoteEntity.priority,
                    unlocked = remoteEntity.unlocked,
                    palette = remoteEntity.palette ?: ExtendedPalette()
                )
                else localEntity
            }
        }
        Log.d("Palette", "Fetched ${mergedModels.size} palettes")
        mergedModels.forEach {
            Log.d("Palette", "Fetched $it")
        }
    }

    override fun getPalettes(): List<MarketPalette> {
        return cache.toExternal()
    }

    override fun initialSetupEvent() {
        dataStoreSource.initialSetupEvent()
    }

    override suspend fun initFlow(): Flow<PaletteDatastore.PalettePreferences> =
        dataStoreSource.flow

    override suspend fun saveCurrentPalette(uuid: String) {
        dataStoreSource.savePalette(uuid)
    }

    init {
        initialSetupEvent()

        CoroutineScope(coroutineDispatcher).launch {
            cache = getLocalPalettes()
            synchronizePalettes()
        }
    }

}
