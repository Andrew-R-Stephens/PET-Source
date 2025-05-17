package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.core.model.MarketBundle
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.core.model.MarketBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.core.repository.MarketRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.Palette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface PaletteRepository: MarketRepository {
    val defaultPalette: PaletteEntity
    val marketPalettes: MutableMap<String, Palette>
    val localPalettes: Map<String, ExtendedPalette>
    var _allPalettes: MutableStateFlow<MutableMap<String, Palette>>
    var allPalettes: StateFlow<MutableMap<String, Palette>>
    val marketBundles: MutableMap<String, MarketBundle>

    suspend fun fetchAllThemes()
    suspend fun fetchAllBundles()
    fun unifyRemoteWithLocalPalettes()
    suspend fun fetchAllRemoteThemes(): List<PaletteEntity>
    suspend fun fetchAllRemoteBundles(): List<MarketBundleEntity>
    suspend fun populatePalettesRemote()
    suspend fun populateBundlesRemote()
    fun populatePalettesLocal()
    fun fetchAllLocalThemes(): List<Pair<String, ExtendedPalette>>
}