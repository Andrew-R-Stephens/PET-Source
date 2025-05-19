package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper.toExternal
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.api.FirestoreTypographyApi.Companion.getAllTypographys
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.TypographyDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class TypographyRemoteDataSource : TypographyDataSource<List<NetworkTypographyEntity>> {

    override suspend fun fetchAll(): List<NetworkTypographyEntity> =
        withContext(Dispatchers.IO) {

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

                                val dto = MarketTypographyDto(
                                    uuid = uuid,
                                    name = name,
                                    group = group,
                                    buyCredits = buyCredits
                                )

                                val typographyEntity = dto.toExternal()
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

            typographiesList


        }

}