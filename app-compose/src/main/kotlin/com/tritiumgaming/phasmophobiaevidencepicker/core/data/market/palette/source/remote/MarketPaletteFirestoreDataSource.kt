package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.bundle.source.remote.MarketBundleFirestoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto.MarketPaletteDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.collections.forEach

class MarketPaletteFirestoreDataSource {

    fun getPaletteCollection(
        merchandiseDocument: DocumentReference
    ): CollectionReference = merchandiseDocument
        .collection(COLLECTION_PALETTES)

    suspend fun query(
        merchandiseDocument: DocumentReference,
        options: PaletteQueryOptions = PaletteQueryOptions()
    ): List<MarketPaletteDto> = withContext(Dispatchers.IO) {

        val palettes = mutableListOf<MarketPaletteDto>()

        try {
            createQuery(merchandiseDocument, options)
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

                            //val paletteDto = dto.toNetwork()
                            palettes.add(dto)

                        } catch (e: Exception) {
                            Log.d("Firestore", "Error obtaining remote palettes!")
                            e.printStackTrace()
                        }

                    }

                }
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
        }

        palettes

    }

    private fun createQuery(
        merchandiseDocument: DocumentReference,
        options: PaletteQueryOptions
    ): Task<QuerySnapshot> {

        var query: Query = getPaletteCollection(merchandiseDocument)

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

    data class PaletteQueryOptions(
        val filterField: FilterField? = FilterField.GROUP,
        val filterValue: FilterValue? = FilterValue.NONE,
        val orderField: OrderField? = OrderField.NONE,
        val orderDirection: Query.Direction? = Query.Direction.DESCENDING,
        val limit: Limit? = Limit.UNLIMITED
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
            UNLIMITED(Int.MAX_VALUE)
        }
    }

    private companion object {
        private const val COLLECTION_PALETTES = "Themes"
    }

}