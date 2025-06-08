package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MarketTypographyFirestoreDataSource(
    private val firestore: FirebaseFirestore
) {

    private val storeCollectionRef: CollectionReference
        get() = firestore.collection(COLLECTION_STORE)

    private val merchandiseDocumentRef: DocumentReference = storeCollectionRef
        .document(DOCUMENT_MERCHANDISE)

    private val typographyCollection: CollectionReference = merchandiseDocumentRef
        .collection(COLLECTION_TYPOGRAPHIES)

    suspend fun fetch(
        typographyQueryOptions: TypographyQueryOptions = TypographyQueryOptions()
    ): Result<List<MarketTypographyDto>> = withContext(Dispatchers.IO) {

        try {
            val typographies = mutableListOf<MarketTypographyDto>()

            createQuery(typographyQueryOptions)
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

        query = if(options.filterField?.value != null && options.filterValue?.value != null) {
            query.whereEqualTo(options.filterField.value, options.filterValue.value)
        } else query

        query = if(options.orderField?.value != null && options.orderDirection != null) {
            query.whereEqualTo(options.orderField.value, options.filterValue?.value)
        } else query

        query = if(options.limit?.value != null) {
            query.limit(options.limit.value.toLong())
        } else query

        return query
            .get()
    }

    data class TypographyQueryOptions(
        val filterField: FilterField? = FilterField.GROUP,
        val filterValue: FilterValue? = FilterValue.NONE,
        val orderField: OrderField? = OrderField.NONE,
        val orderDirection: Query.Direction? = Query.Direction.DESCENDING,
        val limit: Limit? = Limit.SAFE_LIMIT
    ) {
        enum class FilterField(val value: String?) {
            GROUP("group"),
            NONE(null)
        }
        enum class FilterValue(val value: String?) {
            NONE(null)
        }
        enum class OrderField(val value: String?) {
            NONE(null)
        }
        enum class Limit(val value: Int) {
            SAFE_LIMIT(50),
            UNLIMITED(Int.MAX_VALUE)
        }
    }

    private companion object {
        private const val COLLECTION_STORE = "Store"
        private const val DOCUMENT_MERCHANDISE = "Merchandise"
        private const val COLLECTION_TYPOGRAPHIES = "Typographies"
    }

}