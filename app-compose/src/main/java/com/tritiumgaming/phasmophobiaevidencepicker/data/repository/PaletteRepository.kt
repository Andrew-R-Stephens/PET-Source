package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.network.NetworkMarketDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketBundle
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketBundleEntity
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketPaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ExtendedPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalDefaultPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalettesMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PaletteRepository(
    val dataStore: DataStore<Preferences>,
    context: Context,
    private val networkSource: NetworkMarketDataSource = NetworkMarketDataSource(),
    private val localSource: Map<String, ExtendedPalette> = LocalPalettesMap
) {

    val flow: Flow<ColorPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) { emit(emptyPreferences()) }
            else { throw exception }
        }
        .map { preferences ->
            mapPreferences(preferences)
        }

    //val marketPalettes = mutableListOf<MarketPalette>()
    val defaultPalette = MarketPaletteEntity(
        uuid = LocalDefaultPalette.uuid,
        name = context.getString(LocalDefaultPalette.palette.extrasFamily.title),
        group = "Default",
        unlocked = true
    )

    val marketPalettes: MutableMap<String, MarketPalette> = mutableMapOf()
    private val localPalettes = fetchAllLocalThemes().toMap()

    private var _allPalettes = MutableStateFlow(mutableMapOf<String, MarketPalette>())
    var allPalettes = _allPalettes.asStateFlow()

    val marketBundles: MutableMap<String, MarketBundle> = mutableMapOf()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchAllThemes()
            fetchAllBundles()
        }
    }

    private suspend fun fetchAllThemes() {
        populatePalettesLocal()
        populatePalettesRemote()

        unifyRemoteWithLocalPalettes()
    }

    private suspend fun fetchAllBundles() {
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

    private fun unifyRemoteWithLocalPalettes() {
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

    private suspend fun fetchAllRemoteThemes(): List<MarketPaletteEntity> =
        networkSource.fetchAllPalettes()

    private suspend fun fetchAllRemoteBundles(): List<MarketBundleEntity> =
        networkSource.fetchAllBundles()

    private suspend fun populatePalettesRemote() {
        val remoteEntities: List<MarketPaletteEntity> = fetchAllRemoteThemes()
        remoteEntities.forEach { remoteEntity ->
            localPalettes[remoteEntity.uuid]?.let { localTheme ->

                val palette = MarketPalette(
                    marketThemeEntity = remoteEntity,
                    palette = localTheme
                )

                marketPalettes[remoteEntity.uuid] = palette
            }
        }
    }

    private suspend fun populateBundlesRemote() {
        val remoteEntities: List<MarketBundleEntity> = fetchAllRemoteBundles()
        remoteEntities.forEach { remoteEntity ->

            val bundle = MarketBundle(
                marketBundleEntity = remoteEntity
            )

            marketBundles[remoteEntity.uuid] = bundle

        }
    }

    private fun populatePalettesLocal() {
        localPalettes.toList().forEach {
            allPalettes.value[it.first] = MarketPalette(
                uuid = it.first, palette = it.second
            )
        }
    }

    private fun fetchAllLocalThemes(): List<Pair<String, ExtendedPalette>> = localSource.toList()

    suspend fun savePalette(uuid: String) {
        dataStore.edit { preferences ->
            Log.d("ColorThemeHandler", "Saving ${preferences[KEY_PALETTE]} -> $uuid")
            preferences[KEY_PALETTE] = uuid
            Log.d("ColorThemeHandler", "Finalizing $uuid == ${preferences[KEY_PALETTE]} : ${uuid == preferences[KEY_PALETTE]}")
        }
    }

    init {
        Log.d("ColorTheme Repository", "Initializing")

        KEY_PALETTE = stringPreferencesKey(context.resources.getString(R.string.preference_savedTheme))
    }

    suspend fun fetchInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    private fun mapPreferences(preferences: Preferences): ColorPreferences {
        return ColorPreferences(
            preferences[KEY_PALETTE] ?: ""
        )
    }

    data class ColorPreferences(
        val uuid: String
    )

    companion object PreferenceKeys {
        lateinit var KEY_PALETTE: Preferences.Key<String>
    }

}
