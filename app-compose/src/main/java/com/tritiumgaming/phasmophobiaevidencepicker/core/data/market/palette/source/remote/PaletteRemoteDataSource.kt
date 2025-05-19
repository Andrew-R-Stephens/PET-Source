package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto.MarketPaletteDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto.toExternal
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.api.FirestorePaletteApi
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PaletteRemoteDataSource : PaletteDataSource<List<NetworkPaletteEntity>> {

    override suspend fun fetchAll(): List<NetworkPaletteEntity> =
        withContext(Dispatchers.IO) {

            val themesList = mutableListOf<NetworkPaletteEntity>()

            try {
                FirestorePaletteApi.Companion.getAllThemes()
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

                                val paletteEntity = dto.toExternal()
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

            themesList

        }

}