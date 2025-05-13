package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.market.palette

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.model.market.bundle.MarketBundle
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.model.market.bundle.MarketBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.model.market.palette.Palette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.model.market.palette.PaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.market.core.ThemeRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface PaletteRepository: ThemeRepository {
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