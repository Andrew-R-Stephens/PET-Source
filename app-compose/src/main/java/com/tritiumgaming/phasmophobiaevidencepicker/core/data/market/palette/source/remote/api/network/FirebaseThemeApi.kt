package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.network

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.dto.theming.MarketBundleDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.dto.theming.MarketThemeDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.NetworkPaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.firestore.transactions.store.merchandise.bundles.FirestoreMerchandiseBundle.Companion.getAllBundles
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.firestore.transactions.store.merchandise.palettes.FirestoreMerchandisePalettes.Companion.getAllThemes
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.api.firestore.transactions.store.merchandise.typographies.FirestoreMerchandiseTypographies.Companion.getAllTypographys
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.NetworkTypographyEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.model.NetworkBundleEntity
import kotlinx.coroutines.tasks.await

class StoreThemesApi: ThemeApi {

    override suspend fun fetchAllPalettes(): List<NetworkPaletteEntity> {

        val themesList = mutableListOf<NetworkPaletteEntity>()

        try {
            getAllThemes()
                .await()
                .documents
                .forEach { documentSnapshot: DocumentSnapshot ->
                    if (!documentSnapshot.exists()) {
                        Log.d("Firestore", "Theme document snapshot DNE.")
                    } else {
                        val uuid = documentSnapshot.reference.id
                        try {
                            var name = ""
                            var group = ""
                            var buyCredits = 0L

                            documentSnapshot.data?.let { map ->
                                map["name"]?.let { name = it as String }
                                map["group"]?.let { group = it as String }
                                map["buyCredits"]?.let { buyCredits = it as Long }
                            }

                            val paletteDto = MarketThemeDto(
                                uuid = uuid,
                                name = name,
                                group = group,
                                buyCredits = buyCredits
                            )
                            val paletteEntity = paletteDto.toMarketPaletteEntity()
                            themesList.add(paletteEntity)
                        } catch (e: Exception) {
                            Log.d("Firestore", "Error obtaining remote palettes!")
                            e.printStackTrace()
                        }
                    }
                }
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
        }

        return themesList

    }

    override suspend fun fetchAllBundles(): List<NetworkBundleEntity> {

        val bundleList = mutableListOf<NetworkBundleEntity>()

        try {
            getAllBundles()
                .await()
                .documents
                .forEach { documentSnapshot: DocumentSnapshot ->
                    if (!documentSnapshot.exists()) {
                        Log.d("Firestore", "Bundle document snapshot DNE.")
                    } else {
                        val uuid = documentSnapshot.reference.id
                        try {
                            var name = ""
                            var buyCredits = 0L
                            val themeUUIDs = mutableListOf<String>()

                            documentSnapshot.data?.let { map ->
                                map["name"]?.let { name = it as String }
                                map["buyCredits"]?.let { buyCredits = it as Long }

                                (map["items"] as List<*>?)?.forEach {
                                    if (it is DocumentReference) {
                                        themeUUIDs.add(it.id)
                                    }
                                }
                            }

                            val paletteDto = MarketBundleDto(
                                uuid = uuid,
                                name = name,
                                buyCredits = buyCredits,
                                items = themeUUIDs
                            )
                            val bundleEntity = paletteDto.toMarketBundleEntity()
                            bundleList.add(bundleEntity)

                        } catch (e: Exception) {
                            Log.d("Firestore", "Error obtaining remote palettes!")
                            e.printStackTrace()
                        }
                    }

                }
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
        }

        return bundleList

    }

    override suspend fun fetchAllTypographies(): List<NetworkTypographyEntity> {

        val typographiesList = mutableListOf<NetworkTypographyEntity>()

        try {
            getAllTypographys()
                .await()
                .documents
                .forEach { documentSnapshot: DocumentSnapshot ->
                    if (!documentSnapshot.exists()) {
                        Log.d("Firestore", "Theme document snapshot DNE.")
                    } else {
                        val uuid = documentSnapshot.reference.id
                        try {
                            var name = ""
                            var group = ""
                            var buyCredits = 0L

                            documentSnapshot.data?.let { map ->
                                map["name"]?.let { name = it as String }
                                map["group"]?.let { group = it as String }
                                map["buyCredits"]?.let { buyCredits = it as Long }
                            }

                            val paletteDto = MarketThemeDto(
                                uuid = uuid,
                                name = name,
                                group = group,
                                buyCredits = buyCredits
                            )
                            val typographyEntity = paletteDto.toMarketTypographyEntity()
                            typographiesList.add(typographyEntity)
                        } catch (e: Exception) {
                            Log.d("Firestore", "Error obtaining remote palettes!")
                            e.printStackTrace()
                        }
                    }
                }
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
        }

        return typographiesList

    }

}

// Makes news-related network synchronous requests.
interface ThemeApi {
    suspend fun fetchAllPalettes(): List<NetworkPaletteEntity>
    suspend fun fetchAllBundles(): List<NetworkBundleEntity>
    suspend fun fetchAllTypographies(): List<NetworkTypographyEntity>
}