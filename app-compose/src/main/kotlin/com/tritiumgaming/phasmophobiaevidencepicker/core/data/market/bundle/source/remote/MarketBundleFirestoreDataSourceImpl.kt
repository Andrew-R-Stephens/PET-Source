package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.dto.MarketBundleDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.model.BundleQueryOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MarketBundleFirestoreDataSourceImpl(
    private val firestore: FirebaseFirestore
): MarketBundleFirestoreDataSource {

    val storeCollectionRef: CollectionReference
    get() = firestore.collection(COLLECTION_STORE)

    val merchandiseDocumentRef: DocumentReference = storeCollectionRef
        .document(DOCUMENT_MERCHANDISE)

    val bundlesCollection: CollectionReference = merchandiseDocumentRef
        .collection(COLLECTION_BUNDLES)

    override suspend fun fetch(
        options: BundleQueryOptions
    ): Result<List<MarketBundleDto>> = withContext(Dispatchers.IO) {

        val bundles = mutableListOf<MarketBundleDto>()

        try {
            createQuery(options)
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

                            val dto = MarketBundleDto(
                                uuid = uuid,
                                name = name,
                                buyCredits = buyCredits,
                                items = themeUUIDs
                            )

                            bundles.add(dto)

                        } catch (e: Exception) {
                            Log.d("Firestore", "Error obtaining remote bundles!")
                            e.printStackTrace()
                            return@withContext Result.failure(
                                Exception("Error obtaining remote bundles!", e))
                        }
                    }

                }

            Result.success(bundles)

        } catch (e: FirebaseFirestoreException) {
            Result.failure(Exception("Error obtaining remote bundles!", e))
        }

    }

    private fun createQuery(
        options: BundleQueryOptions
    ): Task<QuerySnapshot> {

        var query: Query = bundlesCollection

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
        private const val COLLECTION_BUNDLES = "Bundles"
    }

}