package com.tritiumgaming.data.palette.source.remote

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.data.palette.dto.MarketTypographyDto
import com.tritiumgaming.shared.core.domain.market.common.source.MarketFirestoreDataSource
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyQueryOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MarketTypographyFirestoreDataSource(
    private val firestore: FirebaseFirestore
): MarketFirestoreDataSource<MarketTypographyDto, TypographyQueryOptions> {

    private val storeCollectionRef: CollectionReference
        get() = firestore.collection(COLLECTION_STORE)

    private val merchandiseDocumentRef: DocumentReference = storeCollectionRef
        .document(DOCUMENT_MERCHANDISE)

    private val typographyCollection: CollectionReference = merchandiseDocumentRef
        .collection(COLLECTION_TYPOGRAPHIES)

    override suspend fun fetch(
        queryOptions: TypographyQueryOptions
    ): Result<List<MarketTypographyDto>> = withContext(Dispatchers.IO) {

        try {
            val typographies = mutableListOf<MarketTypographyDto>()

            createQuery(queryOptions)
                .await()
                .documents
                .forEach { documentSnapshot: DocumentSnapshot ->
                    if (!documentSnapshot.exists()) {
                        Log.d("Firestore", "Typographies document snapshot DNE.")
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

                            typographies.add(dto)

                        } catch (e: Exception) {
                            Log.d("Firestore", "Error obtaining remote typographies!")
                            e.printStackTrace()
                            return@withContext Result.failure(
                                Exception("Error obtaining remote typographies!", e))
                        }
                    }
                }

            Result.success(typographies)

        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            Result.failure(Exception("Error obtaining remote typographies!", e))
        }

    }

    private fun createQuery(
        options: TypographyQueryOptions
    ): Task<QuerySnapshot> {

        var query: Query = typographyCollection

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
        private const val COLLECTION_TYPOGRAPHIES = "Typographies"
    }

}