package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.dto.MarketBundleDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.source.api.references.FirestoreBundleApi.Companion.bundlesCollection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.source.BundleDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class BundleRemoteDataSource: BundleDataSource {

    override suspend fun fetchAll(): List<MarketBundleDto> =
        withContext(Dispatchers.IO) {

            val bundleList = mutableListOf<MarketBundleDto>()

            try {
                getAllBundles()
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

                                bundleList.add(dto)

                            } catch (e: Exception) {
                                Log.d("Firestore", "Error obtaining remote palettes!")
                                e.printStackTrace()
                            }
                        }

                    }
            } catch (e: FirebaseFirestoreException) {
                e.printStackTrace()
            }

            bundleList

        }

    fun getBundleWhere() {
        TODO("Not yet implemented")
    }

    fun getBundleWhere(
        filterField: String? = null,
        value: String? = null,
        orderField: String? = null,
        order: Query.Direction? = Query.Direction.DESCENDING
    ): Task<QuerySnapshot> {
        var query: Query = bundlesCollection
        if (filterField != null && value != null) {
            query = bundlesCollection
                .whereEqualTo(filterField, value)
        }

        if (orderField != null && order != null) {
            return query
                .orderBy(orderField, order)
                .get()
        }

        return query.get()
    }

    fun getAllBundles(
        orderField: String? = null,
        order: Query.Direction? = Query.Direction.DESCENDING
    ): Task<QuerySnapshot> {
        val query = bundlesCollection

        if (orderField == null || order == null) {
            return query.get()
        }

        return query
            .orderBy(orderField, order)
            .get()
    }

}