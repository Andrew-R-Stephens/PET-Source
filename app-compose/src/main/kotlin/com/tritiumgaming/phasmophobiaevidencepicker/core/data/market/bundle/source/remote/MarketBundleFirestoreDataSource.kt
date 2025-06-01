package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.source.remote.MarketBillableFirestoreDataSource.BillableQueryOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.dto.MarketBundleDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.collections.forEach

class MarketBundleFirestoreDataSource {

    fun getBundlesCollection(
        merchandiseDocument: DocumentReference
    ): CollectionReference = merchandiseDocument
        .collection(COLLECTION_BUNDLES)

    suspend fun query(
        merchandiseDocument: DocumentReference,
        options: BundleQueryOptions = BundleQueryOptions()
    ): List<MarketBundleDto> = withContext(Dispatchers.IO) {

        val bundles = mutableListOf<MarketBundleDto>()

        try {
            createQuery(merchandiseDocument, options)
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
                        }
                    }

                }
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
        }

        bundles

    }

    private fun createQuery(
        merchandiseDocument: DocumentReference,
        options: BundleQueryOptions
    ): Task<QuerySnapshot> {

        var query: Query = getBundlesCollection(merchandiseDocument)

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

    data class BundleQueryOptions(
        val filterField: FilterField? = FilterField.NONE,
        val filterValue: FilterValue? = FilterValue.NONE,
        val orderField: OrderField? = OrderField.NONE,
        val orderDirection: Query.Direction? = Query.Direction.DESCENDING,
        val limit: Limit? = Limit.UNLIMITED
    ) {
        enum class FilterField(val value: String?) {
            NONE(null)
        }
        enum class FilterValue(val value: String?) {
            NONE(null)
        }
        enum class OrderField(val value: String?) {
            NONE(null)
        }
        enum class Limit(val value: Int) {
            UNLIMITED(Int.MAX_VALUE)
        }
    }

    private companion object {
        private const val COLLECTION_BUNDLES = "Bundles"
    }

}