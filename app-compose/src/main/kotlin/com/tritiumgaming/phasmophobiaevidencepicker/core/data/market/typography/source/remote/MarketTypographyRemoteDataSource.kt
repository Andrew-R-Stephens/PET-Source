package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.api.MarketTypographyApi.Companion.getAllTypographys
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MarketTypographyRemoteDataSource : MarketTypographyDataSource<List<MarketTypographyDto>> {

    override suspend fun fetchAll(): List<MarketTypographyDto> =
        withContext(Dispatchers.IO) {

            val typographiesList = mutableListOf<MarketTypographyDto>()

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

                                val dto = MarketTypographyDto(
                                    uuid = uuid,
                                    name = name,
                                    group = group,
                                    buyCredits = buyCredits
                                )

                                //val typographyEntity = dto.toNetwork()
                                typographiesList.add(dto)

                            } catch (e: Exception) {
                                Log.d("Firestore", "Error obtaining remote palettes!")
                                e.printStackTrace()
                            }
                        }
                    }
            } catch (e: FirebaseFirestoreException) {
                e.printStackTrace()
            }

            typographiesList


        }

}