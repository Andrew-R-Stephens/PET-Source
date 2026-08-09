package com.tritiumgaming.data.marketplace.palette.source.remote

import com.google.firebase.functions.FirebaseFunctions
import com.tritiumgaming.data.marketplace.palette.dto.MarketPaletteDto
import com.tritiumgaming.shared.data.market.palette.model.PaletteQueryOptions
import kotlinx.coroutines.tasks.await
import kotlin.collections.get

class MarketPaletteFirestoreDataSource(
    private val firebaseFunctions: FirebaseFunctions,
) {

    suspend fun fetch(
        options: PaletteQueryOptions = PaletteQueryOptions()
    ): Result<List<MarketPaletteDto>> {

        return try {
            val data = hashMapOf(
                "filterField" to options.filterField.value,
                "filterValue" to options.filterValue.value,
                "orderField" to options.orderField.value,
                "orderDirection" to options.orderDirection.name,
                "limit" to options.limit.value
            )

            val result = firebaseFunctions
                .getHttpsCallable("fetchPalettes")
                .call(data)
                .await()

            val palettes = mutableListOf<MarketPaletteDto>()

            val resultList = result.data as? List<*> ?: emptyList<Any>()

            resultList.forEach { item ->
                (item as? Map<*, *>)?.let { map ->
                    val uuid = (map["uuid"] as? String) ?: ""
                    val name = (map["name"] as? String) ?: ""
                    val group = (map["group"] as? String) ?: ""
                    val buyCredits = (map["buyCredits"] as? Number)?.toLong() ?: 0L

                    palettes.add(
                        MarketPaletteDto(
                            uuid = uuid,
                            name = name,
                            group = group,
                            buyCredits = buyCredits
                        )
                    )
                }
            }

            Result.success(palettes)

        } catch (e: Exception) {
            Result.failure(Exception("Error obtaining remote palettes!", e))
        }

    }

}