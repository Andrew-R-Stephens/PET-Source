package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.source.remote

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.dto.MarketBillableDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MarketBillableFirestoreDataSource(
    private val firestore: FirebaseFirestore
) {

    val storeCollectionRef: CollectionReference
        get() = firestore.collection(COLLECTION_STORE)

    private val microTransactionsDocument: DocumentReference = storeCollectionRef
        .document(DOCUMENT_MICRO_TRANSACTIONS)

    private val billableCollection: CollectionReference = microTransactionsDocument
        .collection(COLLECTION_BILLABLE)

    suspend fun query(
        billableQueryOptions: BillableQueryOptions = BillableQueryOptions()
    ): List<MarketBillableDto> = withContext(Dispatchers.IO) {

        val billables = mutableListOf<MarketBillableDto>()

        try {
            createQuery(billableQueryOptions)
                .await()
                .documents
                .forEach { documentSnapshot: DocumentSnapshot ->
                    if (!documentSnapshot.exists()) {
                        Log.d("Firestore", "Typographies document snapshot DNE.")
                    } else {

                        try {

                            var productId = ""
                            var type = ""
                            var tier = 0
                            var rewardAmount = 0
                            var rewardItem = ""
                            var activeStatus = false

                            documentSnapshot.data?.let { map ->
                                map["product_id"]?.let { productId = it as String }
                                map["type"]?.let { type = it as String }
                                map["tier"]?.let { tier = it as Int }
                                map["reward_amount"]?.let { rewardAmount = it as Int }
                                map["reward_item"]?.let { rewardItem = it as String }
                                map["active_status"]?.let { activeStatus = it as Boolean }
                            }

                            val dto = MarketBillableDto(
                                productId,
                                type,
                                tier,
                                rewardAmount,
                                rewardItem,
                                activeStatus
                            )

                            //val paletteDto = dto.toNetwork()
                            billables.add(dto)

                        } catch (e: Exception) {
                            Log.d("Firestore", "Error obtaining remote typographies!")
                            e.printStackTrace()
                        }

                    }

                }
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
        }

        billables

    }

    private fun createQuery(
        options: BillableQueryOptions
    ): Task<QuerySnapshot> {

        var query: Query = billableCollection

        query = if(options.filterField?.value != null && options.filterValue?.value != null) {
            query.whereEqualTo(options.filterField.value, options.filterValue.value)
        } else query

        query = if(options.orderField?.value != null && options.orderDirection != null) {
            query.whereEqualTo(options.orderField.value, options.orderDirection)
        } else query

        query = if(options.limit?.value != null) {
            query.limit(options.limit.value.toLong())
        } else query

        return query
            .get()
    }

    data class BillableQueryOptions(
        val filterField: FilterField? = FilterField.NONE,
        val filterValue: FilterValue? = FilterValue.NONE,
        val orderField: OrderField? = OrderField.NONE,
        val orderDirection: Query.Direction? = Query.Direction.DESCENDING,
        val limit: Limit? = Limit.UNLIMITED
    ) {
        enum class FilterField(val value: String?) {
            TYPE("type"),
            NONE(null)
        }
        enum class FilterValue(val value: String?) {
            CREDITS("credits"),
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
        private const val COLLECTION_STORE = "Store"
        private const val DOCUMENT_MICRO_TRANSACTIONS = "Microtransactions"
        private const val COLLECTION_BILLABLE = "Billables"
    }

}