package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.repository

import android.content.Context
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote.MarketRemoteDataSourceInterface
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.PaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.ThemeRepository.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.local.PaletteLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalDefaultPalette
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.market.bundle.MarketBundle
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.market.bundle.MarketBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.market.palette.Palette
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.market.palette.PaletteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PaletteRepository(
    context: Context,
    private val remoteDataSource: MarketRemoteDataSourceInterface,
    private val localDataSource: PaletteLocalDataSource
): PaletteRepository {

    override val defaultPalette = PaletteEntity(
        uuid = LocalDefaultPalette.uuid,
        name = context.getString(LocalDefaultPalette.palette.extrasFamily.title),
        group = "Default",
        unlocked = true
    )

    override val marketPalettes: MutableMap<String, Palette> = mutableMapOf()
    override val localPalettes = fetchAllLocalThemes().toMap()

    override var _allPalettes = MutableStateFlow(mutableMapOf<String, Palette>())
    override var allPalettes = _allPalettes.asStateFlow()

    override val marketBundles: MutableMap<String, MarketBundle> = mutableMapOf()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchAllThemes()
            fetchAllBundles()
        }
    }

    override suspend fun fetchAllThemes() {
        populatePalettesLocal()
        populatePalettesRemote()

        unifyRemoteWithLocalPalettes()
    }

    override suspend fun fetchAllBundles() {
        populateBundlesRemote()

        marketBundles.toList()
            .forEach {
                val entity = it.second.marketBundleEntity
                Log.d(
                    "Bundle",
                    "${it.first} -> ${entity?.uuid} ${entity?.name}"
                )
                entity?.items?.forEach{ paletteUUID ->
                    Log.d(
                        "Palette", "\t${paletteUUID}"
                    )
                }
            }
    }

    override fun unifyRemoteWithLocalPalettes() {
        marketPalettes.toList().forEach {
            allPalettes.value[it.first] = it.second
        }

        allPalettes.value.toList()
            .sortedBy { it.second.marketThemeEntity?.priority }
            .forEach {
                val entity = it.second.marketThemeEntity
                val palette = it.second.palette
                Log.d(
                    "Palette",
                    "${it.first} -> ${entity?.uuid} ${entity?.name} / ${palette.extrasFamily.title}"
                )
            }
    }

    override suspend fun fetchAllRemoteThemes(): List<PaletteEntity> =
        remoteDataSource.fetchAllPalettes()

    override suspend fun fetchAllRemoteBundles(): List<MarketBundleEntity> =
        remoteDataSource.fetchAllBundles()

    override suspend fun populatePalettesRemote() {
        val remoteEntities: List<PaletteEntity> = fetchAllRemoteThemes()
        remoteEntities.forEach { remoteEntity ->
            localPalettes[remoteEntity.uuid]?.let { localTheme ->

                val palette = Palette(
                    marketThemeEntity = remoteEntity,
                    palette = localTheme
                )

                marketPalettes[remoteEntity.uuid] = palette
            }
        }
    }

    override suspend fun populateBundlesRemote() {
        val remoteEntities: List<MarketBundleEntity> = fetchAllRemoteBundles()
        remoteEntities.forEach { remoteEntity ->

            val bundle = MarketBundle(
                marketBundleEntity = remoteEntity
            )

            marketBundles[remoteEntity.uuid] = bundle

        }
    }

    override fun populatePalettesLocal() {
        localPalettes.toList().forEach {
            allPalettes.value[it.first] = Palette(
                uuid = it.first, palette = it.second
            )
        }
    }

    override fun fetchAllLocalThemes(): List<Pair<String, ExtendedPalette>> = localDataSource.palettes.toList()

    override fun findNextAvailable(
        currentUUID: StateFlow<String>,
        direction: IncrementDirection
    ): String {

        Log.d("Settings", "${currentUUID.value} ${allPalettes.value.size}")
        if(allPalettes.value.isEmpty()) return ""

        val filtered = allPalettes.value
            .filter {
                it.value.marketThemeEntity == null
                        || it.value.marketThemeEntity?.unlocked == true
            }

        Log.d("Settings", "Filtered: ${filtered.size}")
        if(filtered.isEmpty()) return ""


        val list = filtered.keys.toList()
        val currentIndex = list.indexOf(currentUUID.value)

        var increment = currentIndex + direction.value
        if(increment >= list.size) increment = 0
        if(increment < 0) increment = list.size - 1

        Log.d("Settings", "Move: $currentIndex $increment $direction")

        return list[increment]
    }

}
