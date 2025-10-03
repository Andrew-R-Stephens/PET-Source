package com.tritiumgaming.data.marketplace.billable.source.remote

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.data.marketplace.billable.dto.MarketBillableDto
import com.tritiumgaming.data.marketplace.billable.source.MarketBillableRemoteDataSource
import com.tritiumgaming.shared.core.domain.market.billable.model.BillableQueryOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MarketBillableFirestoreDataSource(
    private val firestore: FirebaseFirestore
): MarketBillableRemoteDataSource {

    val storeCollectionRef: CollectionReference
        get() = firestore.collection(COLLECTION_STORE)

    private val microTransactionsDocument: DocumentReference = storeCollectionRef
        .document(DOCUMENT_MICRO_TRANSACTIONS)

    private val billableCollection: CollectionReference = microTransactionsDocument
        .collection(COLLECTION_BILLABLE)

    override suspend fun fetch(
        billableQueryOptions: BillableQueryOptions
    ): Result<List<MarketBillableDto>> = withContext(Dispatchers.IO) {

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
                            return@withContext Result.failure(
                                Exception("Error obtaining remote typographies!", e))
                        }

                    }

                }

            Result.success(billables)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            Result.failure(Exception("Error obtaining remote typographies!", e))
        }

    }

    private fun createQuery(
        options: BillableQueryOptions
    ): Task<QuerySnapshot> {

        var query: Query = billableCollection

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
        private const val DOCUMENT_MICRO_TRANSACTIONS = "Microtransactions"
        private const val COLLECTION_BILLABLE = "Billables"
    }

}