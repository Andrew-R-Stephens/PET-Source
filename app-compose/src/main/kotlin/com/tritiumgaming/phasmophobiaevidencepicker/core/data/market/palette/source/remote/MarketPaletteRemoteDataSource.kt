package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto.MarketPaletteDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.api.MarketPaletteCollectionApi
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.RemotePaletteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MarketPaletteRemoteDataSource : RemotePaletteDataSource<List<MarketPaletteDto>> {

    override suspend fun fetchPalettes(): List<MarketPaletteDto> =
        withContext(Dispatchers.IO) {

            val themesList = mutableListOf<MarketPaletteDto>()

            try {
                MarketPaletteCollectionApi.Companion.getAllThemes()
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

                                val dto = MarketPaletteDto(
                                    uuid = uuid,
                                    name = name,
                                    group = group,
                                    buyCredits = buyCredits
                                )

                                //val paletteDto = dto.toNetwork()
                                themesList.add(dto)

                            } catch (e: Exception) {
                                Log.d("Firestore", "Error obtaining remote palettes!")
                                e.printStackTrace()
                            }
                        }
                    }
            } catch (e: FirebaseFirestoreException) {
                e.printStackTrace()
            }

            themesList

        }

}