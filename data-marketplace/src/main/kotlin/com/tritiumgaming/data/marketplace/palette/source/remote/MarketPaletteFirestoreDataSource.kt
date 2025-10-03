package com.tritiumgaming.data.marketplace.palette.source.remote

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.data.marketplace.palette.dto.MarketPaletteDto
import com.tritiumgaming.shared.core.domain.market.palette.model.PaletteQueryOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MarketPaletteFirestoreDataSource(
    private val firestore: FirebaseFirestore
) {

    val storeCollectionRef: CollectionReference
        get() = firestore.collection(COLLECTION_STORE)

    val merchandiseDocumentRef: DocumentReference = storeCollectionRef
        .document(DOCUMENT_MERCHANDISE)

    val paletteCollection: CollectionReference = merchandiseDocumentRef
        .collection(COLLECTION_PALETTES)

    suspend fun fetch(
        options: PaletteQueryOptions = PaletteQueryOptions()
    ): Result<List<MarketPaletteDto>> = withContext(Dispatchers.IO) {

        try {
            val palettes = mutableListOf<MarketPaletteDto>()

            createQuery(options)
                .await()
                .documents
                .forEach { documentSnapshot: DocumentSnapshot ->
                    if (!documentSnapshot.exists()) {
                        Log.d("Firestore", "Palette document snapshot DNE.")
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

                            palettes.add(dto)

                        } catch (e: Exception) {
                            Log.d("Firestore", "Error obtaining remote palettes!")
                            e.printStackTrace()
                            return@withContext Result.failure(
                                Exception("Error obtaining remote palettes!", e))
                        }
                    }
                }

            Result.success(palettes)

        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            Result.failure(Exception("Error obtaining remote palettes!", e))
        }

    }

    private fun createQuery(
        options: PaletteQueryOptions
    ): Task<QuerySnapshot> {

        var query: Query = paletteCollection

        val filterField = options.filterField.value
        query = if(filterField != null && options.filterValue.value != null) {
            query.whereEqualTo(filterField, options.filterValue.value)
        } else query

        val orderField = options.orderField.value
        query = if(orderField != null) {
            query.whereEqualTo(orderField, options.orderDirection)
        } else query

        query = query.limit(options.limit.value.toLong())

        return query
            .get()
    }

    private companion object {
        private const val COLLECTION_STORE = "Store"
        private const val DOCUMENT_MERCHANDISE = "Merchandise"
        private const val COLLECTION_PALETTES = "Themes"
    }

}